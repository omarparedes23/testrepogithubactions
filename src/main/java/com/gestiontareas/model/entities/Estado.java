package com.gestiontareas.model.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "estado")
public class Estado {

    public Estado(String estado, String tipo) {
        this.estado = estado;
        this.tipo = estado;
    }

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    @Column(name = "id_estado")
    private int id;


    @Column(name = "estado_nombre", length = 20, nullable = false)
    private String estado;

    @Column(name = "tipo_estado", length = 20, nullable = false)
    private String tipo;

    @Override
    public String toString() {
        return "Estado{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
