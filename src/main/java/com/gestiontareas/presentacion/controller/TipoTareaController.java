package com.gestiontareas.presentacion.controller;

import com.gestiontareas.presentacion.dto.TipoTareaDto;
import com.gestiontareas.service.interfaces.ITipoTareaService;
import com.gestiontareas.utils.response.ResponseCreated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tipo de Tarea", description = "Gestion de tipos de tarea")
@RestController
@RequestMapping("/api/v1/tipotarea")
public class TipoTareaController {
    @Autowired
    private ITipoTareaService tipoTareaService;

    // all TipoTarea
    @Operation(summary = "Listar tipos de tarea", description = "Obtiene todos los tipos de tarea registrados")
    @GetMapping(value = "all")
    public ResponseEntity<List<TipoTareaDto>> findTipoTarea(){
        return new ResponseEntity<>(this.tipoTareaService.findAll(), HttpStatus.OK);
    }

    // one TipoTarea
    @Operation(summary = "Obtener tipo de tarea por id", description = "Obtiene un tipo de tarea a partir de su identificador")
    @GetMapping(value = "one/{id}")
    public ResponseEntity<TipoTareaDto> findByIdTipoTarea(@Parameter(description = "Id del tipo de tarea") @PathVariable Integer id){
        return new ResponseEntity<>(this.tipoTareaService.findById(id), HttpStatus.OK);
    }

    // Create TipoTarea
    @Operation(summary = "Crear tipo de tarea", description = "Registra un nuevo tipo de tarea")
    @PostMapping(value = "create")
    public ResponseEntity<ResponseCreated> createTipoTarea(@RequestBody TipoTareaDto tipoTareaDto){
        return new ResponseEntity<>(this.tipoTareaService.create(tipoTareaDto), HttpStatus.CREATED);
    }

    // update TipoTarea
    @Operation(summary = "Actualizar tipo de tarea", description = "Actualiza un tipo de tarea existente a partir de su identificador")
    @PutMapping(value = "update/{id}")
    public ResponseEntity<ResponseCreated> updateTipoTarea(@RequestBody TipoTareaDto tipoTareaDto, @Parameter(description = "Id del tipo de tarea") @PathVariable Integer id){
        return new ResponseEntity<>(this.tipoTareaService.update(tipoTareaDto, id), HttpStatus.OK);
    }

    // delete TipoTarea
    @Operation(summary = "Eliminar tipo de tarea", description = "Elimina un tipo de tarea a partir de su identificador")
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<ResponseCreated> deleteTipoTarea(@Parameter(description = "Id del tipo de tarea") @PathVariable Integer id){
        return new ResponseEntity<>(this.tipoTareaService.delete(id), HttpStatus.OK);
    }
}
