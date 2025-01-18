package com.alura.foro_api.domain.respuestas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CrearRespuestaDTO(
        @NotBlank(message = "El mensaje no puede estar vacío") String mensaje,

        @NotNull(message = "El ID del usuario no puede ser nulo") Long usuarioId,

        @NotNull(message = "El ID del tópico no puede ser nulo") Long topicoId
) {
}
