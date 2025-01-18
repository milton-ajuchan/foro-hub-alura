package com.alura.foro_api.domain.respuestas.validaciones.actualizar;


import com.alura.foro_api.domain.respuestas.ActualizarRespuestaDTO;

public interface ValidarRespuestaActualizada {

    void validate(ActualizarRespuestaDTO data, Long respuestaId);

}