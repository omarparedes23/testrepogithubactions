package com.gestiontareas.presentacion.controller;

import com.gestiontareas.presentacion.dto.ComentarioDto;
import com.gestiontareas.service.interfaces.IComentarioService;
import com.gestiontareas.utils.response.ResponseCreated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Comentario", description = "Gestion de comentarios de tareas")
@RestController
@RequestMapping("/api/v1/comentario")
public class ComentarioController {
    @Autowired
    private IComentarioService comentarioService;

    // all Comentario
    @Operation(summary = "Listar comentarios", description = "Obtiene todos los comentarios registrados")
    @GetMapping(value = "all")
    public ResponseEntity<List<ComentarioDto>> findComentario(){
        return new ResponseEntity<>(this.comentarioService.findAll(), HttpStatus.OK);
    }

    // one Comentario
    @Operation(summary = "Obtener comentario por id", description = "Obtiene un comentario a partir de su identificador")
    @GetMapping(value = "one/{id}")
    public ResponseEntity<ComentarioDto> findByIdComentario(@Parameter(description = "Id del comentario") @PathVariable Integer id){
        return new ResponseEntity<>(this.comentarioService.findById(id), HttpStatus.OK);
    }

    // Create Comentario
    @Operation(summary = "Crear comentario", description = "Registra un nuevo comentario")
    @PostMapping(value = "create")
    public ResponseEntity<ResponseCreated> createComentario(@RequestBody ComentarioDto comentarioDto){
        return new ResponseEntity<>(this.comentarioService.create(comentarioDto), HttpStatus.CREATED);
    }

    // update Comentario
    @Operation(summary = "Actualizar comentario", description = "Actualiza un comentario existente a partir de su identificador")
    @PutMapping(value = "update/{id}")
    public ResponseEntity<ResponseCreated> updateComentario(@RequestBody ComentarioDto comentarioDto, @Parameter(description = "Id del comentario") @PathVariable Integer id){
        return new ResponseEntity<>(this.comentarioService.update(comentarioDto, id), HttpStatus.OK);
    }

    // delete Comentario
    @Operation(summary = "Eliminar comentario", description = "Elimina un comentario a partir de su identificador")
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<ResponseCreated> deleteComentario(@Parameter(description = "Id del comentario") @PathVariable Integer id){
        return new ResponseEntity<>(this.comentarioService.delete(id), HttpStatus.OK);
    }
}
