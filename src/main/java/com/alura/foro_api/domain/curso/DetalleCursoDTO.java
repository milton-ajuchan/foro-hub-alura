package com.alura.foro_api.domain.curso;

import lombok.Getter;
import lombok.AllArgsConstructor;


 // DTO para transferir la información detallada de un curso.

@Getter
@AllArgsConstructor
public class DetalleCursoDTO {

    private final Long id;
    private final String nombre;
    private final Categoria categoria;
    private final Boolean activo;


    public DetalleCursoDTO(Curso curso) {
        this(curso.getId(), curso.getNombre(), curso.getCategoria(), curso.getActivo());
    }
}
