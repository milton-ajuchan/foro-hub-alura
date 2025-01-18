package com.alura.foro_api.domain.topicos.validaciones.crear;

import com.alura.foro_api.domain.topicos.CrearTopicoDTO;

public interface ValidarTopicoCreado {

    void validate(CrearTopicoDTO data);
}