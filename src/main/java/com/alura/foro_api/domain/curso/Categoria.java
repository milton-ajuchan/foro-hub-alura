package com.alura.foro_api.domain.curso;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class Categoria {

    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio.")
    @Size(max = 100, message = "El nombre de la categoría debe tener como máximo 100 caracteres.")
    private String nombre;

    @Size(max = 255, message = "La descripción debe tener como máximo 255 caracteres.")
    private String descripcion;


    // Constructor
    public Categoria(Long id, String nombre, String descripcion) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    // Métodos útiles como toString(), equals(), y hashCode()
    @Override
    public String toString() {
        return "Categoria{id=" + id + ", nombre='" + nombre + "', descripcion='" + descripcion + "'}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Categoria categoria = (Categoria) obj;
        return id != null && id.equals(categoria.id);
    }

    @Override
    public int hashCode() {
        return 31 * (id != null ? id.hashCode() : 0);
    }
}
