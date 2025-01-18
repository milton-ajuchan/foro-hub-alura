package com.alura.foro_api.domain.respuestas.validaciones.crear;

import com.alura.foro_api.domain.respuestas.CrearRespuestaDTO;

public interface ValidarRespuestaCreada {
    void validate(CrearRespuestaDTO data);
}