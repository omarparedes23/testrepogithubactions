package com.gestiontareas.presentacion.dto;

import com.gestiontareas.model.entities.Tarea;
import com.gestiontareas.model.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDto {
    private int id;
    private TareaDto tarea;
    private UsuarioDto usuario;
    private LocalDateTime fecha;
    private String comentario;
}
