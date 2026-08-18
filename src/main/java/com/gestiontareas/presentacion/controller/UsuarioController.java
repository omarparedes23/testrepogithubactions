package com.gestiontareas.presentacion.controller;


import com.gestiontareas.presentacion.dto.UsuarioDto;
import com.gestiontareas.service.interfaces.IStorageService;
import com.gestiontareas.service.interfaces.IUsuarioService;
import com.gestiontareas.utils.response.ResponseCreated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Tag(name = "Usuario", description = "Gestion de usuarios y archivos asociados")
@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UsuarioController {


    @Autowired
    private IUsuarioService usuarioService;

    @Autowired
    private  IStorageService storageService;




    // all User
    @Operation(summary = "Listar usuarios", description = "Obtiene todos los usuarios registrados")
    @GetMapping(value = "all")
    public ResponseEntity<List<UsuarioDto>> findUser(){
        return new ResponseEntity<>(this.usuarioService.findAll(), HttpStatus.OK);
    }

    // one User
    @Operation(summary = "Obtener usuario por id", description = "Obtiene un usuario a partir de su identificador")
    @GetMapping(value = "one/{id}")
    public ResponseEntity<UsuarioDto> findByIdUser(@Parameter(description = "Id del usuario") @PathVariable Integer id){
        return new ResponseEntity<>(this.usuarioService.findById(id), HttpStatus.OK);
    }

    // Create User
    @Operation(summary = "Crear usuario", description = "Registra un nuevo usuario")
    @PostMapping(value = "create")
    public ResponseEntity<ResponseCreated> createUser(@RequestBody UsuarioDto userDto){
        return new ResponseEntity<>(this.usuarioService.createUser(userDto), HttpStatus.CREATED);
    }

    // update User
    @Operation(summary = "Actualizar usuario", description = "Actualiza un usuario existente a partir de su identificador")
    @PutMapping(value = "update/{id}")
    public ResponseEntity<ResponseCreated> updateUser(@RequestBody UsuarioDto userDto, @Parameter(description = "Id del usuario") @PathVariable Integer id){
   return new ResponseEntity<>(this.usuarioService.updateUser(userDto, id), HttpStatus.OK);
    }

    // delete User
    @Operation(summary = "Eliminar usuario", description = "Elimina un usuario a partir de su identificador")
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<ResponseCreated> deleteUser(@Parameter(description = "Id del usuario") @PathVariable Integer id){
        return new ResponseEntity<>(this.usuarioService.deleteUser(id), HttpStatus.OK);
    }


    @Operation(
            summary = "Subir archivo",
            description = "Sube un archivo (por ejemplo, foto de perfil) y lo almacena en S3, devolviendo la URL de acceso",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(type = "string", format = "binary"))
            )
    )
    @PostMapping(value = "upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, String> uploadFile(@RequestParam("file") MultipartFile multipartFile,  HttpServletRequest request){
        String path = storageService.store(multipartFile);
        String host = request.getRequestURL().toString().replace(request.getRequestURI(),"");
        String url = ServletUriComponentsBuilder
                .fromHttpUrl(host)
                .path("/api/v1/user/")
                .path(path)
                .toUriString();

        return Map.of("url",url);

    }

    // El recurso ahora vive en S3: en vez de leer bytes locales (Resource#getFile()
    // solo funciona con archivos en disco), redirigimos al cliente a la URL
    // prefirmada de corta duracion que genera S3StorageServiceImpl.
    @Operation(summary = "Obtener archivo", description = "Redirige a la URL prefirmada de S3 para descargar el archivo solicitado")
    @GetMapping(value = "{filename:.+}")
    public ResponseEntity<Void> getFile(@Parameter(description = "Nombre del archivo") @PathVariable String filename) {
        try {
            Resource file = storageService.loadResource(filename);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, file.getURL().toString())
                    .build();
        } catch (IOException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "File not found", ex);
        }
    }

   

}
