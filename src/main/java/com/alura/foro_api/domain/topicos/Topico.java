package com.alura.foro_api.domain.topicos;

import com.alura.foro_api.domain.curso.Curso;
import com.alura.foro_api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "topicos")
@Entity(name = "Topico")
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;

    @Column(name="fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name="ultima_actualizacion")
    private LocalDateTime ultimaActualizacion; //add date now

    @Setter
    @Enumerated(EnumType.STRING)
    private Estado estado;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Topico(CrearTopicoDTO crearTopicoDTO, Usuario usuario, Curso curso) {
        this.titulo = crearTopicoDTO.titulo();
        this.mensaje = crearTopicoDTO.mensaje();
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
        this.estado = Estado.OPEN;
        this.usuario = usuario;
        this.curso = curso;
    }



    public void actualizarTopicoConCurso(ActualizarTopicoDTO actualizarTopicoDTO, Curso curso) {
        if (actualizarTopicoDTO.titulo() != null){
            this.titulo = actualizarTopicoDTO.titulo();
        }
        if (actualizarTopicoDTO.mensaje() != null){
            this.mensaje = actualizarTopicoDTO.mensaje();
        }
        if (actualizarTopicoDTO.estado() != null){
            this.estado = actualizarTopicoDTO.estado();
        }
        if (actualizarTopicoDTO.cursoId() != null){
            this.curso = curso;
        }
        this.ultimaActualizacion = LocalDateTime.now();

    }

    public void actualizarTopico(ActualizarTopicoDTO actualizarTopicoDTO){
        if (actualizarTopicoDTO.titulo() != null){
            this.titulo = actualizarTopicoDTO.titulo();
        }
        if (actualizarTopicoDTO.mensaje() != null){
            this.mensaje = actualizarTopicoDTO.mensaje();
        }
        if(actualizarTopicoDTO.estado() != null){
            this.estado = actualizarTopicoDTO.estado();
        }
        this.ultimaActualizacion = LocalDateTime.now();
    }

    public void eliminarTopico(){
        this.estado = Estado.DELETED;
    }

}