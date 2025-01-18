package com.alura.foro_api.domain.topicos.validaciones.crear;

import com.alura.foro_api.domain.curso.CursoRepository;
import com.alura.foro_api.domain.topicos.CrearTopicoDTO;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidarCursoCreado implements ValidarTopicoCreado {

    @Autowired
    private CursoRepository repository;

    @Override
    public void validate(CrearTopicoDTO data) {
        // Verificar si el curso existe en el sistema y si está habilitado para su uso
        var curso = repository.findById(data.cursoId())
                .orElseThrow(() -> new ValidationException("El curso con el ID proporcionado no se encuentra registrado en el sistema."));

        // Comprobar si el curso está habilitado para ser utilizado en la creación de un tópico
        if (!curso.getActivo()) {
            throw new ValidationException("El curso indicado no se encuentra disponible para la creación de un tópico en este momento.");
        }
    }
}
