package com.alura.foro_api.domain.usuario.validaciones.actualizar;

import com.alura.foro_api.domain.usuario.ActualizarUsuarioDTO;
import com.alura.foro_api.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidacionActualizacionUsuario implements ValidarActualizarUsuario {

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(ActualizarUsuarioDTO data) {
        if (data.email() != null) {

            boolean emailDuplicado = repository.existsByEmail(data.email());
            if (emailDuplicado) {
                throw new ValidationException("El email ya está en uso");
            }
        }
    }
}
