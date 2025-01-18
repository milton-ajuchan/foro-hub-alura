package com.alura.foro_api.domain.usuario;


public record AutenticarUsuarioDTO(
        String usuario,          // Nombre de usuario
        String password          // Contraseña del usuario
) {

    public AutenticarUsuarioDTO {
        if (usuario == null || usuario.isEmpty()) {
            throw new IllegalArgumentException("El nombre de usuario no puede estar vacío.");
        }
        if (password == null || password.isEmpty()) {
            throw new IllegalArgumentException("La contraseña no puede estar vacía.");
        }
    }
}
