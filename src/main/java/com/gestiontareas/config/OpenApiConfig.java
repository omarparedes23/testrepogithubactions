package com.gestiontareas.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI gestionTareasOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Gestion Tareas API")
                        .description("API para control y gestion de tareas")
                        .version("v0.0.2")
                        .contact(new Contact().name("NTT Data - Curso DevOps")))
                // Server relativo ("/"): Swagger UI arma las peticiones contra el
                // mismo origen (protocolo/host/puerto) desde el que se cargo la
                // pagina. Esto evita depender de que el proxy (Cloud Shell, Ingress,
                // load balancer, etc.) reenvie correctamente X-Forwarded-Proto/Host,
                // y funciona igual en local, Docker o detras de cualquier proxy.
                .addServersItem(new Server().url("/"))
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")));
    }
}
