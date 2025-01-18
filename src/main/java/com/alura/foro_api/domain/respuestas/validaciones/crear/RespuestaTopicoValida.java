package com.alura.foro_api.domain.respuestas.validaciones.crear;

import com.alura.foro_api.domain.respuestas.CrearRespuestaDTO;
import com.alura.foro_api.domain.usuario.Usuario;
import com.alura.foro_api.domain.usuario.UsuarioRepository;
import com.alura.foro_api.domain.usuario.excepciones.UsuarioNoExisteException;
import com.alura.foro_api.domain.usuario.excepciones.UsuarioNoHabilitadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaTopicoValida implements ValidarRespuestaCreada {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CrearRespuestaDTO data) {
        // Obtener el usuario y validar su existencia y habilitación en un solo paso
        Usuario usuario = repository.findById(data.usuarioId())
                .orElseThrow(() -> new UsuarioNoExisteException("Este usuario no existe"));

        // Validar si el usuario está habilitado
        if (!usuario.isEnabled()) {
            throw new UsuarioNoHabilitadoException("Este usuario no está habilitado");
        }
    }
}
