package com.alura.foro_api.domain.usuario;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

/**
 * DTO que contiene los detalles de un usuario.
 * Este DTO se utiliza para transferir información detallada de un usuario entre las capas de la aplicación.
 */
public record DetallesUsuarioDTO(
        Long id,                     // ID del usuario
        String username,             // Nombre de usuario
        Role role,                   // Rol del usuario
        @NotNull String nombre,      // Nombre del usuario (No puede ser nulo)
        @NotNull String apellido,    // Apellido del usuario (No puede ser nulo)
        @Email String email,         // Correo electrónico del usuario (Debe ser un email válido)
        Boolean activo               // Indica si el usuario está activo
) {

}
