package com.gestiontareas.presentacion.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoTareaDto {

    private int id;
    private String tipo;
    private String imagen;

}
