package com.alura.foro_api.domain.respuestas;

import com.alura.foro_api.domain.topicos.Topico;
import com.alura.foro_api.domain.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Representa una respuesta a un tema en el foro.
 * Contiene el mensaje, fechas de creación y actualización,
 * y los detalles del usuario y tema asociados.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "respuestas")
@Entity(name = "Respuesta")
@EqualsAndHashCode(of = "id")
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    @Column(name="fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name="ultima_actualizacion")
    private LocalDateTime ultimaActualizacion;

    private Boolean solucion = false;  // Valor por defecto
    private Boolean borrado = false;  // Valor por defecto

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="topico_id")
    private Topico topico;

    /**
     * Constructor que inicializa una nueva respuesta a partir de un DTO.
     * @param crearRespuestaDTO Datos del DTO para la creación de la respuesta.
     * @param usuario Usuario que publica la respuesta.
     * @param topico Tema relacionado con la respuesta.
     */
    public Respuesta(CrearRespuestaDTO crearRespuestaDTO, Usuario usuario, Topico topico) {
        this.mensaje = crearRespuestaDTO.mensaje();
        this.usuario = usuario;
        this.topico = topico;
        this.fechaCreacion = LocalDateTime.now(); // Fecha de creación al momento de la creación
        this.ultimaActualizacion = LocalDateTime.now(); // Fecha de última actualización inicial
    }

    /**
     * Actualiza los datos de la respuesta con la información proporcionada en el DTO.
     * @param actualizarRespuestaDTO DTO que contiene los datos para actualizar la respuesta.
     */
    public void actualizarRespuesta(ActualizarRespuestaDTO actualizarRespuestaDTO){
        if(actualizarRespuestaDTO.mensaje() != null){
            this.mensaje = actualizarRespuestaDTO.mensaje();
        }
        if (actualizarRespuestaDTO.solucion() != null){
            this.solucion = actualizarRespuestaDTO.solucion();
        }
        this.ultimaActualizacion = LocalDateTime.now(); // Actualización de la fecha
    }

    /**
     * Marca la respuesta como eliminada.
     * Esta respuesta no será mostrada en las consultas normales.
     */
    public void eliminarRespuesta(){
        this.borrado = true;
    }

    /**
     * Metodo de ciclo de vida de JPA que se ejecuta antes de insertar en la base de datos.
     * Establece las fechas de creación y última actualización.
     */
    @PrePersist
    public void prePersist() {
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaActualizacion = LocalDateTime.now();
    }

    /**
     * Metodo de ciclo de vida de JPA que se ejecuta antes de actualizar en la base de datos.
     * Establece la fecha de última actualización.
     */
    @PreUpdate
    public void preUpdate() {
        this.ultimaActualizacion = LocalDateTime.now();
    }
}
