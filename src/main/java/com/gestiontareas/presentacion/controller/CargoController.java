package com.gestiontareas.presentacion.controller;


import com.gestiontareas.presentacion.dto.CargoDto;
import com.gestiontareas.service.interfaces.ICargoService;
import com.gestiontareas.utils.response.ResponseCreated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cargo", description = "Gestion de cargos de usuario")
@RestController
@RequestMapping("/api/v1/cargo")
public class CargoController {
    @Autowired
    private ICargoService cargoService;

    // all Cargo uploa uploadd
    @Operation(summary = "Listar cargos", description = "Obtiene todos los cargos registrados")
    @GetMapping(value = "all")
    public ResponseEntity<List<CargoDto>> findCargo(){
        return new ResponseEntity<>(this.cargoService.findAll(), HttpStatus.OK);
    }

    // one Cargo
    @Operation(summary = "Obtener cargo por id", description = "Obtiene un cargo a partir de su identificador")
    @GetMapping(value = "one/{id}")
    public ResponseEntity<CargoDto> findByIdCargo(@Parameter(description = "Id del cargo") @PathVariable Integer id){
        return new ResponseEntity<>(this.cargoService.findById(id), HttpStatus.OK);
    }

    // Create Cargo
    @Operation(summary = "Crear cargo", description = "Registra un nuevo cargo")
    @PostMapping(value = "create")
    public ResponseEntity<ResponseCreated> createCargo(@RequestBody CargoDto cargoDto){
        return new ResponseEntity<>(this.cargoService.create(cargoDto), HttpStatus.CREATED);
    }

    // update Cargo
    @Operation(summary = "Actualizar cargo", description = "Actualiza un cargo existente a partir de su identificador")
    @PutMapping(value = "update/{id}")
    public ResponseEntity<ResponseCreated> updateCargo(@RequestBody CargoDto cargoDto, @Parameter(description = "Id del cargo") @PathVariable Integer id){
        return new ResponseEntity<>(this.cargoService.update(cargoDto, id), HttpStatus.OK);
    }

    // delete Cargo
    @Operation(summary = "Eliminar cargo", description = "Elimina un cargo a partir de su identificador")
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<ResponseCreated> deleteCargo(@Parameter(description = "Id del cargo") @PathVariable Integer id){
        return new ResponseEntity<>(this.cargoService.delete(id), HttpStatus.OK);
    }
}
