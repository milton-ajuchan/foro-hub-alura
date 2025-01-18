package com.alura.foro_api.domain.usuario.validaciones.actualizar;

import com.alura.foro_api.domain.usuario.ActualizarUsuarioDTO;

public interface ValidarActualizarUsuario {
    void validate(ActualizarUsuarioDTO data);
}