package com.alura.foro_api.domain.topicos.validaciones.actualizar;

import com.alura.foro_api.domain.curso.CursoRepository;
import com.alura.foro_api.domain.topicos.ActualizarTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Valida los datos de un curso antes de actualizar un tópico, asegurándose de que el curso
 * proporcionado existe y está disponible.
 */
@Component
public class ValidarCursoActualizado implements ValidarTopicoActualizado {

    @Autowired
    private CursoRepository cursoRepository;

    /**
     * Valida si el curso relacionado con el tópico es válido para ser actualizado.
     * @param data El DTO que contiene los datos a actualizar, incluyendo el cursoId.
     * @throws ValidationException Si el curso no existe o no está disponible.
     */
    @Override
    public void validate(ActualizarTopicoDTO data) {
        // Verifica si se ha proporcionado un cursoId para actualizarlo
        if (data.cursoId() != null) {
            // Comprobar si el curso existe
            var cursoOpt = cursoRepository.findById(data.cursoId());
            if (cursoOpt.isEmpty()) {
                throw new ValidationException("El curso con ID " + data.cursoId() + " no se encuentra registrado.");
            }

            // Comprobar si el curso está habilitado
            var curso = cursoOpt.get();
            if (!curso.getActivo()) {
                throw new ValidationException("El curso con ID " + data.cursoId() + " no está disponible para su uso en este momento.");
            }
        }
    }
}
