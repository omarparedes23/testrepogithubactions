package com.gestiontareas.presentacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UbicacionDto {

    private String id;
    private String ubicacion;
    private String descripcion;
}
