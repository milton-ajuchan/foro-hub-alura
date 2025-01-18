package com.alura.foro_api.controller;

import com.alura.foro_api.domain.respuestas.*;
import com.alura.foro_api.domain.respuestas.validaciones.actualizar.ValidarRespuestaActualizada;
import com.alura.foro_api.domain.respuestas.validaciones.crear.ValidarRespuestaCreada;
import com.alura.foro_api.domain.topicos.Estado;
import com.alura.foro_api.domain.topicos.Topico;
import com.alura.foro_api.domain.topicos.TopicoRepository;
import com.alura.foro_api.domain.usuario.Usuario;
import com.alura.foro_api.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Respuesta", description = "Gestiona las respuestas de los temas, incluida la marca de solución.")

public class RespuestaController {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final RespuestaRepository respuestaRepository;
    private final List<ValidarRespuestaCreada> crearValidadores;
    private final List<ValidarRespuestaActualizada> actualizarValidadores;

    @Autowired
    public RespuestaController(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository,
                               RespuestaRepository respuestaRepository,
                               List<ValidarRespuestaCreada> crearValidadores,
                               List<ValidarRespuestaActualizada> actualizarValidadores) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.respuestaRepository = respuestaRepository;
        this.crearValidadores = crearValidadores;
        this.actualizarValidadores = actualizarValidadores;
    }

    /**
     * Registra una nueva respuesta vinculada a un usuario y tema existente.
     *
     * @param crearRespuestaDTO Los datos para crear la respuesta.
     * @param uriBuilder El generador de la URI para la nueva respuesta.
     * @return ResponseEntity con la respuesta creada y su URI.
     */
    @PostMapping
    @Transactional
    @Operation(summary = "Registra una nueva respuesta asociada a un usuario y un tema existente.")

    public ResponseEntity<DetalleRespuestaDTO> crearRespuesta(@RequestBody @Valid CrearRespuestaDTO crearRespuestaDTO,
                                                              UriComponentsBuilder uriBuilder) {
        crearValidadores.forEach(v -> v.validate(crearRespuestaDTO));

        Usuario usuario = usuarioRepository.getReferenceById(crearRespuestaDTO.usuarioId());
        Topico topico = topicoRepository.findById(crearRespuestaDTO.topicoId())
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));

        var respuesta = new Respuesta(crearRespuestaDTO, usuario, topico);
        respuestaRepository.save(respuesta);

        var uri = uriBuilder.path("/respuestas/{id}").buildAndExpand(respuesta.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetalleRespuestaDTO(respuesta));
    }

    /**
     * Obtiene todas las respuestas de un tema dado con paginación.
     *
     * @param pageable Parámetros de paginación.
     * @param topicoId El ID del tema.
     * @return Respuestas paginadas del tema.
     */
    @GetMapping("/topico/{topicoId}")
    @Operation(summary = "Lee todas las respuestas del tema dado")
    public ResponseEntity<Page<DetalleRespuestaDTO>> leerRespuestaDeTopico(@PageableDefault(size = 5, sort = {"ultimaActualizacion"}, direction = Direction.ASC) Pageable pageable,
                                                                           @PathVariable Long topicoId) {
        Page<DetalleRespuestaDTO> pagina = respuestaRepository.findAllByTopicoId(topicoId, pageable)
                .map(DetalleRespuestaDTO::new);
        return ResponseEntity.ok(pagina);
    }

    /**
     * Obtiene todas las respuestas de un usuario dado con paginación.
     *
     * @param pageable Parámetros de paginación.
     * @param usuarioId El ID del usuario.
     * @return Respuestas paginadas del usuario.
     */
    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Obtiene todas las respuestas publicadas por un usuario específico.")

    public ResponseEntity<Page<DetalleRespuestaDTO>> leerRespuestasDeUsuarios(@PageableDefault(size = 5, sort = {"ultimaActualizacion"}, direction = Direction.ASC) Pageable pageable,
                                                                              @PathVariable Long usuarioId) {
        Page<DetalleRespuestaDTO> pagina = respuestaRepository.findAllByUsuarioId(usuarioId, pageable)
                .map(DetalleRespuestaDTO::new);
        return ResponseEntity.ok(pagina);
    }

    /**
     * Lee una única respuesta por su ID.
     *
     * @param id El ID de la respuesta.
     * @return Respuesta correspondiente al ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene una respuesta específica por su ID.")

    public ResponseEntity<DetalleRespuestaDTO> leerUnaRespuesta(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        var respuestaDTO = convertirADetalleRespuestaDTO(respuesta);
        return ResponseEntity.ok(respuestaDTO);
    }

    /**
     * Actualiza una respuesta existente.
     *
     * @param actualizarRespuestaDTO Los nuevos datos para la respuesta.
     * @param id El ID de la respuesta a actualizar.
     * @return La respuesta actualizada.
     */
    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza el mensaje, la solución o el estado de una respuesta.")

    public ResponseEntity<DetalleRespuestaDTO> actualizarRespuesta(@RequestBody @Valid ActualizarRespuestaDTO actualizarRespuestaDTO,
                                                                   @PathVariable Long id) {
        actualizarValidadores.forEach(v -> v.validate(actualizarRespuestaDTO, id));

        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.actualizarRespuesta(actualizarRespuestaDTO);

        if (actualizarRespuestaDTO.solucion()) {
            Topico topicoResuelto = topicoRepository.getReferenceById(respuesta.getTopico().getId());
            topicoResuelto.setEstado(Estado.CLOSED);
        }

        var respuestaDTO = convertirADetalleRespuestaDTO(respuesta);
        return ResponseEntity.ok(respuestaDTO);
    }

    /**
     * Elimina una respuesta por su ID.
     *
     * @param id El ID de la respuesta a eliminar.
     * @return Respuesta con el estado 204 (sin contenido).
     */
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina una respuesta según su ID.")

    public ResponseEntity<Void> borrarRespuesta(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.getReferenceById(id);
        respuesta.eliminarRespuesta();
        return ResponseEntity.noContent().build();
    }

    /**
       Metodo auxiliar para convertir una respuesta a DetalleRespuestaDTO.
     */
    private DetalleRespuestaDTO convertirADetalleRespuestaDTO(Respuesta respuesta) {
        return new DetalleRespuestaDTO(
                respuesta.getId(),
                respuesta.getMensaje(),
                respuesta.getFechaCreacion(),
                respuesta.getUltimaActualizacion(),
                respuesta.getSolucion(),
                respuesta.getBorrado(),
                respuesta.getUsuario().getId(),
                respuesta.getUsuario().getUsername(),
                respuesta.getTopico().getId(),
                respuesta.getTopico().getTitulo()
        );
    }
}
