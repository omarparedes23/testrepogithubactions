package com.gestiontareas.config.auth.endpublic;



import com.gestiontareas.config.auth.services.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirements;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Autenticacion", description = "Endpoints publicos de login y validacion de token")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    @Autowired
    private AuthService authService;

    // Endpoint publico: no requiere token JWT previo
    @Operation(summary = "Iniciar sesion", description = "Autentica al usuario y devuelve un token JWT")
    @SecurityRequirements
    @PostMapping(value = "login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Validated LoginRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @Operation(summary = "Validar token", description = "Valida si un token JWT sigue siendo valido")
    @SecurityRequirements
    @PostMapping(value = "validate")
    public ResponseEntity<ValidateResponse> validate(@RequestBody  ValidateRequest token){
        return ResponseEntity.ok(authService.validate(token));
    }

}
