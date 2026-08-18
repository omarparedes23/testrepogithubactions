package com.gestiontareas.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comentario")
@Builder
public class Comentario {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_tarea")
    private Tarea tarea;

    @Column(name = "comentario", length = 150, nullable = false)
    private String comentario;

    @Column(name = "fecha_comentario", nullable = false)
    private LocalDateTime fechaComentario;

    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", usuario=" + usuario +
                ", tarea='" + tarea + '\'' +
                ", comentario='" + comentario + '\'' +
                ", fechaCreacion=" + fechaComentario +
                '}';
    }
}

