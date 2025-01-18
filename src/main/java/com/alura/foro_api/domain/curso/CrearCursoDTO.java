package com.alura.foro_api.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CrearCursoDTO(
        @NotBlank(message = "El nombre del curso no puede estar vacío.")
        @Size(max = 255, message = "El nombre del curso no puede tener más de 255 caracteres.")
        String name,

        @NotNull(message = "La categoría no puede ser nula.")
        Categoria categoria
) {
}
