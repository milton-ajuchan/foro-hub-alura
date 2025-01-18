package com.alura.foro_api.controller;

import com.alura.foro_api.domain.curso.Curso;
import com.alura.foro_api.domain.curso.CursoRepository;
import com.alura.foro_api.domain.respuestas.DetalleRespuestaDTO;
import com.alura.foro_api.domain.respuestas.Respuesta;
import com.alura.foro_api.domain.respuestas.RespuestaRepository;
import com.alura.foro_api.domain.topicos.*;
import com.alura.foro_api.domain.topicos.validaciones.actualizar.ValidarTopicoActualizado;
import com.alura.foro_api.domain.topicos.validaciones.crear.ValidarTopicoCreado;
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
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Topic", description = "Representa un tema asociado a un curso y un usuario.")

public class TopicoController {

    private final TopicoRepository topicoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CursoRepository cursoRepository;
    private final RespuestaRepository respuestaRepository;
    private final List<ValidarTopicoCreado> crearValidadores;
    private final List<ValidarTopicoActualizado> actualizarValidadores;

    @Autowired
    public TopicoController(TopicoRepository topicoRepository, UsuarioRepository usuarioRepository,
                            CursoRepository cursoRepository, RespuestaRepository respuestaRepository,
                            List<ValidarTopicoCreado> crearValidadores, List<ValidarTopicoActualizado> actualizarValidadores) {
        this.topicoRepository = topicoRepository;
        this.usuarioRepository = usuarioRepository;
        this.cursoRepository = cursoRepository;
        this.respuestaRepository = respuestaRepository;
        this.crearValidadores = crearValidadores;
        this.actualizarValidadores = actualizarValidadores;
    }

    @PostMapping
    @Transactional
    @Operation(summary = "Crea un nuevo tópico y lo almacena en la base de datos.")

    public ResponseEntity<DetallesTopicoDTO> crearTopico(@RequestBody @Valid CrearTopicoDTO crearTopicoDTO, UriComponentsBuilder uriBuilder) {
        crearValidadores.forEach(v -> v.validate(crearTopicoDTO));

        Usuario usuario = usuarioRepository.findById(crearTopicoDTO.usuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Curso curso = cursoRepository.findById(crearTopicoDTO.cursoId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Topico topico = new Topico(crearTopicoDTO, usuario, curso);
        topicoRepository.save(topico);

        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DetallesTopicoDTO(topico));
    }

    @GetMapping("/all")
    @Operation(summary = "Obtiene todos los tópicos, independientemente de su estado.")

    public ResponseEntity<Page<DetallesTopicoDTO>> leerTodosTopicos(@PageableDefault(size = 5, sort = {"ultimaActualizacion"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DetallesTopicoDTO> pagina = topicoRepository.findAll(pageable).map(DetallesTopicoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping
    @Operation(summary = "Obtiene la lista de tópicos, tanto abiertos como cerrados.")

    public ResponseEntity<Page<DetallesTopicoDTO>> leerTopicosNoEliminados(@PageableDefault(size = 5, sort = {"ultimaActualizacion"}, direction = Sort.Direction.DESC) Pageable pageable) {
        Page<DetallesTopicoDTO> pagina = topicoRepository.findAllByEstadoIsNot(Estado.DELETED, pageable).map(DetallesTopicoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtiene un único tópico mediante su ID.")

    public ResponseEntity<DetallesTopicoDTO> leerUnTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        DetallesTopicoDTO datosTopico = convertirADetallesTopicoDTO(topico);
        return ResponseEntity.ok(datosTopico);
    }

    @GetMapping("/{id}/solucion")
    @Operation(summary = "Obtiene la respuesta marcada como solución del tópico.")

    public ResponseEntity<DetalleRespuestaDTO> leerSolucionTopico(@PathVariable Long id) {
        Respuesta respuesta = respuestaRepository.findByTopicoIdAndSolucionTrue(id)
                .orElseThrow(() -> new RuntimeException("No se encontró una respuesta marcada como solución para este tópico"));


        DetalleRespuestaDTO datosRespuesta = new DetalleRespuestaDTO(respuesta);
        return ResponseEntity.ok(datosRespuesta);
    }

    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza el título, el contenido, el estado o el curso asociado de un tópico")

    public ResponseEntity<DetallesTopicoDTO> actualizarTopico(@RequestBody @Valid ActualizarTopicoDTO actualizarTopicoDTO, @PathVariable Long id) {
        actualizarValidadores.forEach(v -> v.validate(actualizarTopicoDTO));

        Topico topico = topicoRepository.getReferenceById(id);
        if (actualizarTopicoDTO.cursoId() != null) {
            Curso curso = cursoRepository.getReferenceById(actualizarTopicoDTO.cursoId());
            topico.actualizarTopicoConCurso(actualizarTopicoDTO, curso);
        } else {
            topico.actualizarTopico(actualizarTopicoDTO);
        }

        DetallesTopicoDTO datosTopico = convertirADetallesTopicoDTO(topico);
        return ResponseEntity.ok(datosTopico);
    }

    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un tópico de la base de datos")

    public ResponseEntity<Void> eliminarTopico(@PathVariable Long id) {
        Topico topico = topicoRepository.getReferenceById(id);
        topico.eliminarTopico();
        return ResponseEntity.noContent().build();
    }

    // Metodo auxiliar para convertir un Topico a DetallesTopicoDTO
    private DetallesTopicoDTO convertirADetallesTopicoDTO(Topico topico) {
        return new DetallesTopicoDTO(
                topico.getId(),
                topico.getTitulo(),
                topico.getMensaje(),
                topico.getFechaCreacion(),
                topico.getUltimaActualizacion(),
                topico.getEstado(),
                topico.getUsuario().getUsername(),
                topico.getCurso().getNombre(),
                topico.getCurso().getCategoria()
        );
    }
}
