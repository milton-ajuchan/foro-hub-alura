package com.alura.foro_api.domain.respuestas;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {

    // Encuentra todas las respuestas para un tópico dado, paginadas
    Page<Respuesta> findAllByTopicoId(Long topicoId, Pageable pageable);

    // Encuentra todas las respuestas de un usuario, paginadas
    Page<Respuesta> findAllByUsuarioId(Long usuarioId, Pageable pageable);

    // Encuentra todas las respuestas para un tópico específico (por ID), debería ser una lista
    List<Respuesta> findAllByTopicoId(Long topicoId);

    // Metodo que retorna una respuesta solucionada por tópico. Si solo se espera una respuesta solucionada, Optional está bien.
    Optional<Respuesta> findByTopicoIdAndSolucionTrue(Long topicoId);
}
