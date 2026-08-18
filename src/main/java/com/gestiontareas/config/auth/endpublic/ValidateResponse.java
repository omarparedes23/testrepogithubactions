package com.gestiontareas.config.auth.endpublic;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"idValidate"})
public record ValidateResponse(boolean isValidate) {
}
