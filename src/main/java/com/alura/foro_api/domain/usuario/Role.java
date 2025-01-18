package com.alura.foro_api.domain.usuario;

/**
 * Enum que define los roles de un usuario en el sistema.

 * Los roles disponibles son:
 * - ADMINISTRADOR: Tiene privilegios completos.
 * - USUARIO: Puede interactuar y participar en el foro.
 * - EXPECTADOR: Solo puede ver los contenidos sin interactuar.
 */
public enum Role {
    ADMINISTRADOR,  // Rol con privilegios completos para administrar el sistema
    USUARIO,        // Rol estándar para usuarios que participan activamente
    EXPECTADOR;     // Rol para usuarios que solo pueden ver los contenidos

    // Metodo para verificar si el rol es ADMINISTRADOR
    public boolean esAdministrador() {
        return this == ADMINISTRADOR;
    }

    // Metodo para verificar si el rol es USUARIO
    public boolean esUsuario() {
        return this == USUARIO;
    }

    // Metodo para verificar si el rol es EXPECTADOR
    public boolean esExpectador() {
        return this == EXPECTADOR;
    }
}
