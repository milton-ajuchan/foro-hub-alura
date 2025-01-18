package com.alura.foro_api.domain.respuestas.validaciones.actualizar;

import com.alura.foro_api.domain.respuestas.ActualizarRespuestaDTO;
import com.alura.foro_api.domain.respuestas.Respuesta;
import com.alura.foro_api.domain.respuestas.RespuestaRepository;
import com.alura.foro_api.domain.topicos.Estado;
import com.alura.foro_api.domain.topicos.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SolucionDuplicada implements ValidarRespuestaActualizada {

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validate(ActualizarRespuestaDTO data, Long respuestaId) {
        if (data.solucion()) {
            // Obtener la respuesta y el tópico relacionado
            Respuesta respuesta = respuestaRepository.findById(respuestaId)
                    .orElseThrow(() -> new ValidationException("Respuesta no encontrada."));
            var topicoResuelto = topicoRepository.findById(respuesta.getTopico().getId())
                    .orElseThrow(() -> new ValidationException("Tópico no encontrado."));

            // Validar si el tópico ya está cerrado
            if (topicoResuelto.getEstado() == Estado.CLOSED) {
                throw new ValidationException("Este tópico ya ha sido solucionado y está cerrado.");
            }
        }
    }
}
