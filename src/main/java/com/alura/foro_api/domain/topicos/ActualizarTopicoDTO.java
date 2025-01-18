package com.alura.foro_api.domain.topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ActualizarTopicoDTO(
        @NotBlank(message = "El título no puede estar vacío") String titulo,

        @NotBlank(message = "El mensaje no puede estar vacío") String mensaje,

        @NotNull(message = "El estado del tópico debe ser especificado") Estado estado,

        @NotNull(message = "El ID del curso debe ser especificado") Long cursoId
) {
}
