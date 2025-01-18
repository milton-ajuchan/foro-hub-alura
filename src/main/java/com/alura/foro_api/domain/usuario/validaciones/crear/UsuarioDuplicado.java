package com.alura.foro_api.domain.usuario.validaciones.crear;

import com.alura.foro_api.domain.usuario.CrearUsuarioDTO;
import com.alura.foro_api.domain.usuario.UsuarioRepository;
import jakarta.validation.ValidationException;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Setter
@Component
public class UsuarioDuplicado implements ValidarCrearUsuario{

    @Autowired
    private UsuarioRepository repository;

    @Override
    public void validate(CrearUsuarioDTO data) {
        var usuarioDuplicado = repository.findByUsername(data.usuario());
        if(usuarioDuplicado != null){
            throw new ValidationException("El usuario ya existe.");
        }

        var emailDuplicado = repository.findByEmail(data.email());
        if(emailDuplicado != null){
            throw new ValidationException("El email ya existe.");
        }
    }

}