# Gestion y Control de Tareas

- Diagrama de clase

## Documentacion de la API (Swagger / OpenAPI)

Con la aplicacion en ejecucion, la documentacion interactiva esta disponible en:

- Swagger UI: `http://localhost:8080/swagger-ui.html`
- Especificacion OpenAPI (JSON): `http://localhost:8080/v3/api-docs`

Los endpoints protegidos requieren autenticacion JWT. Desde Swagger UI, usa el boton **Authorize**
e ingresa el token con el formato `Bearer <token>` obtenido en `POST /auth/login`.

## Estado del servicio (Actuator)

- Salud: `http://localhost:8080/actuator/health` (publico)
- Info: `http://localhost:8080/actuator/info` (publico)
- Metricas: `http://localhost:8080/actuator/metrics` (requiere autenticacion)
