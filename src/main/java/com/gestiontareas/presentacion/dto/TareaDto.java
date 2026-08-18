package com.gestiontareas.presentacion.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TareaDto {

    private int id;
    private UsuarioDto aprobado;
    private EstadoDto estado;
    private PrioridadDto prioridad;
    private TipoTareaDto tipo;
    private UbicacionDto ubicacion;
    private String titulo;
    private UsuarioDto usuario;
    private boolean requiereCompra;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaFinalizacion;
    private String numero;
    private String descripcion;
}
