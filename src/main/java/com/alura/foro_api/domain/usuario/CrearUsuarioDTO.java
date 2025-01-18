package com.alura.foro_api.domain.usuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


public record CrearUsuarioDTO(
        @NotBlank(message = "El nombre de usuario no puede estar vacío.") String usuario,   // Nombre de usuario
        @NotBlank(message = "La contraseña no puede estar vacía.") String password,      // Contraseña del usuario
        @NotBlank(message = "El nombre no puede estar vacío.") String nombre,            // Nombre del usuario
        @NotBlank(message = "El apellido no puede estar vacío.") String apellido,        // Apellido del usuario
        @NotBlank(message = "El correo electrónico no puede estar vacío.") @Email(message = "El correo electrónico no es válido.") String email
) {
}
