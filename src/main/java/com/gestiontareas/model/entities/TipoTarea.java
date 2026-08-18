package com.gestiontareas.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tipo_tarea")
public class TipoTarea {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_tarea")
    private int id;

    @Column(name = "tipo_tarea_nombre",length = 20, nullable = false)
    private String tipoTareaNombre;

    @Column(name = "imagen", nullable = false)
    private String imagen;

    @Override
    public String toString() {
        return "TipoTarea{" +
                "id=" + id +
                ", nombre='" + tipoTareaNombre + '\'' +
                ", imagen=" + imagen +
                '}';
    }
}
