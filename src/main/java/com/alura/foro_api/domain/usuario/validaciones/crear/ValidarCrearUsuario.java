package com.alura.foro_api.domain.usuario.validaciones.crear;

import com.alura.foro_api.domain.usuario.CrearUsuarioDTO;

public interface ValidarCrearUsuario {
    void validate(CrearUsuarioDTO data);
}
