package com.alura.foro_api.domain.respuestas;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarRespuestaDTO(
        @NotBlank(message = "El mensaje no puede estar vacío") String mensaje,

        @NotNull(message = "La solución debe ser especificada") Boolean solucion,

        @NotNull(message = "El estado de borrado debe ser especificado") Boolean borrado
) {
}