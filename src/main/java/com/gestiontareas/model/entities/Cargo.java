package com.gestiontareas.model.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "cargo")
@Builder
public class Cargo{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cargo")
    private int id;

    @Column(name = "cargo", nullable = false)
    @Enumerated(EnumType.STRING)
    private CargoEnum cargoNombre;
}