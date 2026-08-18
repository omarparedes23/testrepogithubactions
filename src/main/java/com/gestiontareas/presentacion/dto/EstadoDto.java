package com.gestiontareas.presentacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EstadoDto {
    private int id;
    private String estado;
    private String tipo;
}
