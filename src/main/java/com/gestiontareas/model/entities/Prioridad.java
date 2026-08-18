package com.gestiontareas.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "prioridad")
public class Prioridad {

    public Prioridad(String prioridadNombre) {
        this.prioridadNombre = prioridadNombre;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_prioridad")
    private int id;


    @Column(name = "prioridad_nombre", length = 20,nullable = false)
    private String prioridadNombre;

    @Override
    public String toString() {
        return "Prioridad{" +
                "id=" + id +
                ", prioridadNombre='" + prioridadNombre + '\'' +
                '}';
    }
}
