package com.gestiontareas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Clase de arranque de la aplicacion.
 *
 * <p>La carga de datos iniciales (cargos y usuarios de ejemplo) ya no vive aqui:
 * fue movida a {@link com.gestiontareas.config.seed.DataSeeder}, que solo se
 * activa cuando la propiedad {@code app.data-seed.enabled} es {@code true}
 * (ver application.yml / application-dev.yml). Esto evita insertar datos de
 * prueba cada vez que arranca la aplicacion en un entorno real.
 */
@SpringBootApplication
public class    GestiontareasApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestiontareasApplication.class, args);
    }
}