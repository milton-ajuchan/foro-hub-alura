package com.alura.foro_api.domain.usuario.excepciones;

/**
 * Excepción lanzada cuando el usuario está deshabilitado.
 */
public class UsuarioNoHabilitadoException extends RuntimeException {

    // Constructor que acepta un mensaje de error
    public UsuarioNoHabilitadoException(String message) {
        super(message);
    }
}
