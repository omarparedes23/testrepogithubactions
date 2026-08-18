package com.gestiontareas.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ubicacion")
public class Ubicacion {

    public Ubicacion(String ubicacion, String descripcion) {
        this.ubicacion = ubicacion;
        this.descripcion = descripcion;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_ubicacion")
    private int id;

    @Column(name = "ubicacion_nombre",length = 150, nullable = false)
    private String ubicacion;

    @Column(name = "descripcion",length = 150, nullable = false)
    private String descripcion;

    @Override
    public String toString() {
        return "Ubicacion{" +
                "id='" + id + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }
}
