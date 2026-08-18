package com.gestiontareas.presentacion.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gestiontareas.model.entities.Cargo;
import lombok.*;

import java.time.LocalDate;



@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDto {

    private int id;
    private String nombre;
    private String apellido;
    private String username;
    private String password;
    private String telefono;
    private String email;
    private boolean isEnabled;
    private LocalDate fecha;
    private String horario;
    private String imagen;
    private CargoDto cargo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * write-only: el hash de la contrasena nunca se serializa hacia el cliente.
     * Al editar un usuario, si este campo llega vacio/null significa
     * "no cambiar la contrasena"; si trae un valor, se toma como la
     * contrasena en texto plano nueva y se encripta en el servicio.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public CargoDto getCargo() {
        return cargo;
    }

    public void setCargo(CargoDto cargo) {
        this.cargo = cargo;
    }
}
