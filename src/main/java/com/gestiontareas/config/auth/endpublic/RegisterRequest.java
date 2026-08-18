package com.gestiontareas.config.auth.endpublic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String username;
    String password;
    String nombre;
    String apellido;
    String email;
    String telefono;
    LocalDate fechaCreacion;
    String horarioTrabajo;
    String estadoActivo;
    String imagen;
}
