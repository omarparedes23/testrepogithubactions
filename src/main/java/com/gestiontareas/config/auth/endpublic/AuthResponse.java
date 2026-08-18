package com.gestiontareas.config.auth.endpublic;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonPropertyOrder({"username","name","message","token","role","status"})
public record AuthResponse( String token, String role,String username, String name, String message, boolean status) {


}
