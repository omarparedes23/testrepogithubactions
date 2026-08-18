package com.gestiontareas.config.auth.endpublic;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"token"})
public record ValidateRequest(String token) {
}
