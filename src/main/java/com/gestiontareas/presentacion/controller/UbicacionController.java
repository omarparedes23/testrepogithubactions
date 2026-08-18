package com.gestiontareas.presentacion.controller;

import com.gestiontareas.presentacion.dto.UbicacionDto;
import com.gestiontareas.service.interfaces.IUbicacionService;
import com.gestiontareas.utils.response.ResponseCreated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Ubicacion", description = "Gestion de ubicaciones")
@RestController
@RequestMapping("/api/v1/ubicacion")
public class UbicacionController {

    @Autowired
    private IUbicacionService ubicacionService;

    // all Ubicacion
    @Operation(summary = "Listar ubicaciones", description = "Obtiene todas las ubicaciones registradas")
    @GetMapping(value = "all")
    public ResponseEntity<List<UbicacionDto>> findUbicacion(){
        return new ResponseEntity<>(this.ubicacionService.findAll(), HttpStatus.OK);
    }

    // one Ubicacion
    @Operation(summary = "Obtener ubicacion por id", description = "Obtiene una ubicacion a partir de su identificador")
    @GetMapping(value = "one/{id}")
    public ResponseEntity<UbicacionDto> findByIdUbicacion(@Parameter(description = "Id de la ubicacion") @PathVariable Integer id){
        return new ResponseEntity<>(this.ubicacionService.findById(id), HttpStatus.OK);
    }

    // Create Ubicacion
    @Operation(summary = "Crear ubicacion", description = "Registra una nueva ubicacion")
    @PostMapping(value = "create")
    public ResponseEntity<ResponseCreated> createUbicacion(@RequestBody UbicacionDto ubicacionDto){
        return new ResponseEntity<>(this.ubicacionService.create(ubicacionDto), HttpStatus.CREATED);
    }

    // update Ubicacion
    @Operation(summary = "Actualizar ubicacion", description = "Actualiza una ubicacion existente a partir de su identificador")
    @PutMapping(value = "update/{id}")
    public ResponseEntity<ResponseCreated> updateUbicacion(@RequestBody UbicacionDto ubicacionDto, @Parameter(description = "Id de la ubicacion") @PathVariable Integer id){
        return new ResponseEntity<>(this.ubicacionService.update(ubicacionDto, id), HttpStatus.OK);
    }

    // delete Ubicacion
    @Operation(summary = "Eliminar ubicacion", description = "Elimina una ubicacion a partir de su identificador")
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<ResponseCreated> deleteUbicacion(@Parameter(description = "Id de la ubicacion") @PathVariable Integer id){
        return new ResponseEntity<>(this.ubicacionService.delete(id), HttpStatus.OK);
    }
}
