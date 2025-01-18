package com.alura.foro_api.controller;

import com.alura.foro_api.domain.curso.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
@Tag(name = "Curso", description = "Administra los cursos disponibles, que pueden pertenecer a diversas categorías.")

public class CursoController {

    private final CursoRepository repository;

    // Inyección de dependencias por constructor
    @Autowired
    public CursoController(CursoRepository repository) {
        this.repository = repository;
    }

    /**
     * Crea un nuevo curso en la base de datos.
     *
     * @param crearCursoDTO Los datos del curso a crear.
     * @param uriBuilder Utilizado para construir la URI del nuevo recurso.
     * @return ResponseEntity con el curso creado y su URI.
     */
    @PostMapping
    @Transactional
    @Operation(summary = "Registra un nuevo curso en la base de datos.")

    public ResponseEntity<DetalleCursoDTO> crearCurso(@RequestBody @Valid CrearCursoDTO crearCursoDTO,
                                                      UriComponentsBuilder uriBuilder) {
        Curso curso = new Curso(crearCursoDTO);
        repository.save(curso);
        var uri = uriBuilder.path("/cursos/{id}").buildAndExpand(curso.getId()).toUri();

        return ResponseEntity.created(uri).body(new DetalleCursoDTO(curso));
    }

    /**
     * Obtiene todos los cursos con paginación.
     *
     * @param pageable Parámetros de paginación.
     * @return ResponseEntity con los cursos en formato de página.
     */
    @GetMapping("/all")
    @Operation(summary = "Obtiene todos los cursos, sin importar su estado.")

    public ResponseEntity<Page<DetalleCursoDTO>> listarCursos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        Page<DetalleCursoDTO> pagina = repository.findAll(pageable).map(DetalleCursoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    /**
     * Obtiene todos los cursos activos con paginación.
     *
     * @param pageable Parámetros de paginación.
     * @return ResponseEntity con los cursos activos en formato de página.
     */
    @GetMapping
    @Operation(summary = "Obtiene la lista de cursos activos.")

    public ResponseEntity<Page<DetalleCursoDTO>> listarCursosActivos(@PageableDefault(size = 5, sort = {"id"}) Pageable pageable) {
        Page<DetalleCursoDTO> pagina = repository.findAllByActivoTrue(pageable).map(DetalleCursoDTO::new);
        return ResponseEntity.ok(pagina);
    }

    /**
     * Obtiene un curso por su ID.
     *
     * @param id El ID del curso a obtener.
     * @return ResponseEntity con el curso correspondiente.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Obtiene los detalles de un curso mediante su ID.")

    public ResponseEntity<DetalleCursoDTO> listarUnCurso(@PathVariable Long id) {
        Curso curso = repository.findById(id).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        return ResponseEntity.ok(new DetalleCursoDTO(curso));
    }

    /**
     * Actualiza los datos de un curso.
     *
     * @param actualizarCursoDTO Los nuevos datos del curso.
     * @param id El ID del curso a actualizar.
     * @return ResponseEntity con el curso actualizado.
     */
    @PutMapping("/{id}")
    @Transactional
    @Operation(summary = "Actualiza los detalles de un curso, incluyendo nombre, categoría y estado.")

    public ResponseEntity<DetalleCursoDTO> actualizarCurso(@RequestBody @Valid ActualizarCursoDTO actualizarCursoDTO,
                                                           @PathVariable Long id) {
        Curso curso = repository.findById(id).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        curso.actualizarCurso(actualizarCursoDTO);
        return ResponseEntity.ok(new DetalleCursoDTO(curso));
    }

    /**
     * Elimina un curso.
     *
     * @param id El ID del curso a eliminar.
     * @return ResponseEntity con código de estado 204 (sin contenido).
     */
    @DeleteMapping("/{id}")
    @Transactional
    @Operation(summary = "Elimina un curso de la base de datos.")

    public ResponseEntity<Void> eliminarCurso(@PathVariable Long id) {
        Curso curso = repository.findById(id).orElseThrow(() -> new RuntimeException("Curso no encontrado"));
        curso.eliminarCurso();
        return ResponseEntity.noContent().build();
    }
}
