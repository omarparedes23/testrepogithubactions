package com.gestiontareas.presentacion.dto;


import com.gestiontareas.model.entities.CargoEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CargoDto {
    private int id;
    private CargoEnum cargo;
}
