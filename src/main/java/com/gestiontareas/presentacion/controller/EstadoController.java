package com.gestiontareas.presentacion.controller;

import com.gestiontareas.presentacion.dto.EstadoDto;
import com.gestiontareas.service.interfaces.IEstadoService;
import com.gestiontareas.utils.response.ResponseCreated;

import java.util.List;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Estado", description = "Gestion de estados de tarea")
@RestController
@RequestMapping("/api/v1/estado")
public class EstadoController {

    @Autowired
    private IEstadoService estadoService;

    // all Comentario
    @Operation(summary = "Listar estados", description = "Obtiene todos los estados registrados")
    @GetMapping(value = "all")
    public ResponseEntity<List<EstadoDto>> findEstado(){
        return new ResponseEntity<>(this.estadoService.findAll(), HttpStatus.OK);
    }

    // one Comentario
    @Operation(summary = "Obtener estado por id", description = "Obtiene un estado a partir de su identificador")
    @GetMapping(value = "one/{id}")
    public ResponseEntity<EstadoDto> findByIdComentario(@Parameter(description = "Id del estado") @PathVariable Integer id){
        return new ResponseEntity<>(this.estadoService.findById(id), HttpStatus.OK);
    }

    // Create Comentario
    @Operation(summary = "Crear estado", description = "Registra un nuevo estado")
    @PostMapping(value = "create")
    public ResponseEntity<ResponseCreated> createComentario(@RequestBody EstadoDto estadoDto){
        return new ResponseEntity<>(this.estadoService.create(estadoDto), HttpStatus.CREATED);
    }

    // update Comentario
    @Operation(summary = "Actualizar estado", description = "Actualiza un estado existente a partir de su identificador")
    @PutMapping(value = "update/{id}")
    public ResponseEntity<ResponseCreated> updateComentario(@RequestBody EstadoDto estadoDto, @Parameter(description = "Id del estado") @PathVariable Integer id){
        return new ResponseEntity<>(this.estadoService.update(estadoDto, id), HttpStatus.OK);
    }
}
