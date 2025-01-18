package com.alura.foro_api.domain.usuario.excepciones;

/**
 * Excepción lanzada cuando el usuario no existe en la base de datos.
 */
public class UsuarioNoExisteException extends RuntimeException {

    // Constructor que acepta un mensaje de error
    public UsuarioNoExisteException(String message) {
        super(message);
    }
}
