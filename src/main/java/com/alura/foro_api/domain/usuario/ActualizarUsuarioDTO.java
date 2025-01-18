package com.alura.foro_api.domain.usuario;

public record ActualizarUsuarioDTO(
        String password,         // Contraseña del usuario
        Role role,               // Rol del usuario
        String nombre,           // Nombre del usuario
        String apellido,         // Apellido del usuario
        String email,            // Email del usuario
        Boolean enabled          // Indica si el usuario está activo
) {


    public ActualizarUsuarioDTO {
        if (password != null && password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
        if (email != null && !email.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
            throw new IllegalArgumentException("El correo electrónico no es válido.");
        }
    }
}
