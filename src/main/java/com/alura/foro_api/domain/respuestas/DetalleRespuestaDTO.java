package com.alura.foro_api.domain.respuestas;

import java.time.LocalDateTime;


 // DTO para transferir los detalles de una respuesta.

public record DetalleRespuestaDTO(
        Long id,
        String mensaje,
        LocalDateTime fechaCreacion,
        LocalDateTime ultimaActualizacion,
        Boolean solucion,
        Boolean borrado,
        Long usuarioId,
        String username,
        Long topicoId,
        String topicoTitulo
) {


     // Constructor que convierte un objeto Respuesta en un DTO de detalles de respuesta.

    public DetalleRespuestaDTO(Respuesta respuesta) {
        this(
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
