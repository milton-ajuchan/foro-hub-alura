package com.alura.foro_api.domain.curso;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

    Page<Curso> findAllByActivoTrue(Pageable pageable);

    // Buscar cursos por categoría
    Page<Curso> findAllByCategoria(Categoria categoria, Pageable pageable);

    // Buscar cursos por nombre (ignorando mayúsculas/minúsculas)
    Page<Curso> findAllByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    // Buscar cursos inactivos (activo = false)
    Page<Curso> findAllByActivoFalse(Pageable pageable);
}
