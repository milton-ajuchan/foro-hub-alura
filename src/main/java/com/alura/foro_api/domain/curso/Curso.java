package com.alura.foro_api.domain.curso;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cursos")
@Entity(name = "Curso")
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre; // Renombrado para mayor claridad

    @Enumerated(EnumType.STRING)
    private Categoria categoria;

    private Boolean activo;

    // Constructor usando el DTO de creación
    public Curso(CrearCursoDTO crearCursoDTO) {
        this.nombre = capitalizar(crearCursoDTO.name());
        this.categoria = crearCursoDTO.categoria();
        this.activo = true; // Un curso creado está activo por defecto
    }

    // Metodo para actualizar el curso usando el DTO de actualización
    public void actualizarCurso(ActualizarCursoDTO actualizarCursoDTO) {
        if (actualizarCursoDTO.name() != null) {
            this.nombre = capitalizar(actualizarCursoDTO.name());
        }
        if (actualizarCursoDTO.categoria() != null) {
            this.categoria = actualizarCursoDTO.categoria();
        }
        if (actualizarCursoDTO.activo() != null) {
            this.activo = actualizarCursoDTO.activo();
        }
    }

    // Metodo para eliminar un curso (cambiar su estado a inactivo)
    public void eliminarCurso() {
        this.activo = false;
    }

    // Metodo privado para capitalizar correctamente las palabras
    private String capitalizar(String texto) {
        // Validar si el texto es nulo o vacío
        if (texto == null || texto.trim().isEmpty()) {
            return texto;
        }

        // Capitalizar correctamente cada palabra
        String[] palabras = texto.trim().split(" ");
        StringBuilder textoCapitalizado = new StringBuilder();
        for (String palabra : palabras) {
            textoCapitalizado.append(palabra.substring(0, 1).toUpperCase())
                    .append(palabra.substring(1).toLowerCase())
                    .append(" ");
        }

        // Eliminar el espacio adicional al final
        return textoCapitalizado.toString().trim();
    }
}
