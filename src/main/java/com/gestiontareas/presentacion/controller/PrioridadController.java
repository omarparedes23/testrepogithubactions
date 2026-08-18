package com.gestiontareas.presentacion.controller;

import com.gestiontareas.presentacion.dto.PrioridadDto;
import com.gestiontareas.service.interfaces.IPrioridadService;
import com.gestiontareas.utils.response.ResponseCreated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Prioridad", description = "Gestion de prioridades de tarea")
@RestController
@RequestMapping("/api/v1/prioridad")
public class PrioridadController {
    @Autowired
    private IPrioridadService prioridadService;

    // all Prioridad
    @Operation(summary = "Listar prioridades", description = "Obtiene todas las prioridades registradas")
    @GetMapping(value = "all")
    public ResponseEntity<List<PrioridadDto>> findPrioridad(){
        return new ResponseEntity<>(this.prioridadService.findAll(), HttpStatus.OK);
    }

    // one Prioridad
    @Operation(summary = "Obtener prioridad por id", description = "Obtiene una prioridad a partir de su identificador")
    @GetMapping(value = "one/{id}")
    public ResponseEntity<PrioridadDto> findByIdPrioridad(@Parameter(description = "Id de la prioridad") @PathVariable Integer id){
        return new ResponseEntity<>(this.prioridadService.findById(id), HttpStatus.OK);
    }

    // Create Prioridad
    @Operation(summary = "Crear prioridad", description = "Registra una nueva prioridad")
    @PostMapping(value = "create")
    public ResponseEntity<ResponseCreated> createPrioridad(@RequestBody PrioridadDto prioridadDto){
        return new ResponseEntity<>(this.prioridadService.create(prioridadDto), HttpStatus.CREATED);
    }

    // update Prioridad
    @Operation(summary = "Actualizar prioridad", description = "Actualiza una prioridad existente a partir de su identificador")
    @PutMapping(value = "update/{id}")
    public ResponseEntity<ResponseCreated> updatePrioridad(@RequestBody PrioridadDto prioridadDto, @Parameter(description = "Id de la prioridad") @PathVariable Integer id){
        return new ResponseEntity<>(this.prioridadService.update(prioridadDto, id), HttpStatus.OK);
    }

    // delete Prioridad
    @Operation(summary = "Eliminar prioridad", description = "Elimina una prioridad a partir de su identificador")
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<ResponseCreated> deletePrioridad(@Parameter(description = "Id de la prioridad") @PathVariable Integer id){
        return new ResponseEntity<>(this.prioridadService.delete(id), HttpStatus.OK);
    }
}
