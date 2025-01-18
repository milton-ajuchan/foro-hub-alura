package com.alura.foro_api.domain.topicos.validaciones.crear;

import com.alura.foro_api.domain.topicos.CrearTopicoDTO;
import com.alura.foro_api.domain.topicos.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoDuplicado implements ValidarTopicoCreado {

    @Autowired
    private TopicoRepository topicoRepository;

    @Override
    public void validate(CrearTopicoDTO data) {
        // Verificar si ya existe un topico con el mismo título y mensaje
        boolean topicoDuplicado = topicoRepository.existsByTituloAndMensaje(data.titulo(), data.mensaje());

        if (topicoDuplicado) {
            // Si existe, lanzar una excepción
            throw new ValidationException("Ya existe un tópico con el título '" + data.titulo() + "' y el mensaje proporcionado. Por favor, revisa el tópico antes de intentar crear uno nuevo.");

        }
    }
}
