package com.gestiontareas.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tarea")
@Builder
public class Tarea {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarea")
    private int id;

    @Column(name = "titulo",length = 100, nullable = false)
    private String titulo;

    @Column(name = "descripcion", length = 200, nullable = false)
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Column(name = "requiere_compra", nullable = true)
    private boolean requiereCompra;

    @Column(name = "fecha_creacion", nullable = false)
    private LocalDateTime fechaCreacion;

    @ManyToOne
    @JoinColumn(name = "aprobado")
    private Usuario aprobado;

    @Column(name = "fecha_finalizacion")
    private LocalDateTime fechaFinalizacion;

    @ManyToOne
    @JoinColumn(name = "id_tipo_tarea")
    private TipoTarea tipoTarea;

    @ManyToOne
    @JoinColumn(name = "id_estado")
    private Estado estado;

    @ManyToOne
    @JoinColumn(name = "id_prioridad")
    private Prioridad prioridad;

    @ManyToOne
    @JoinColumn(name = "id_ubicacion")
    private Ubicacion ubicacion;

    @Column(name = "numero_tarea",length = 20, nullable = false)
    private String numeroTarea;

    @Override
    public String toString() {
        return "Tarea{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", usuario=" + usuario +
                ", requiereCompra=" + requiereCompra +
                ", fechaCreacion=" + fechaCreacion +
                ", aprobado=" + aprobado +
                ", fechaFinalizacion=" + fechaFinalizacion +
                ", tipoTarea=" + tipoTarea +
                ", estado=" + estado +
                ", prioridad=" + prioridad +
                ", ubicacion=" + ubicacion +
                '}';
    }
}
