package com.alura.foro_api.domain.topicos.validaciones.crear;

import com.alura.foro_api.domain.topicos.CrearTopicoDTO;
import com.alura.foro_api.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarTopicoUsuario implements ValidarTopicoCreado {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CrearTopicoDTO data) {
        var usuario = repository.findById(data.usuarioId())
                .orElseThrow(() -> new ValidationException("El usuario con ID " + data.usuarioId() + " no existe"));

        if (!usuario.getEnabled()) {
            throw new ValidationException("El usuario con ID " + data.usuarioId() + " está deshabilitado.");
        }
    }
}
