package com.alura.foro_api.domain.topicos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CrearTopicoDTO(
        @NotBlank(message = "El título no puede estar vacío")
        @Size(min = 5, max = 100, message = "El título debe tener entre 5 y 100 caracteres")
        String titulo,

        @NotBlank(message = "El mensaje no puede estar vacío")
        @Size(min = 10, max = 1000, message = "El mensaje debe tener entre 10 y 1000 caracteres")
        String mensaje,

        @NotNull(message = "El ID del usuario no puede ser nulo")
        Long usuarioId,

        @NotNull(message = "El ID del curso no puede ser nulo")
        Long cursoId
) {
}
