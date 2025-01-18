package com.alura.foro_api.domain.topicos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TopicoRepository extends JpaRepository<Topico, Long> {

    // Paginación simple para obtener todos los tópicos
    Page<Topico> findAll(Pageable pageable);

    // Paginación para obtener tópicos cuyo estado no sea el proporcionado
    Page<Topico> findAllByEstadoIsNot(Estado estado, Pageable pageable);

    // Verificar existencia de un tópico basado en el título y mensaje
    Boolean existsByTituloAndMensaje(String titulo, String mensaje);

    // Buscar tópico por título, con un resultado opcional
    Optional<Topico> findByTitulo(String titulo);

    // Buscar tópicos por estado
    Page<Topico> findAllByEstado(Estado estado, Pageable pageable);

    // Buscar tópicos por estado y curso
    Page<Topico> findAllByEstadoAndCursoId(Estado estado, Long cursoId, Pageable pageable);
}