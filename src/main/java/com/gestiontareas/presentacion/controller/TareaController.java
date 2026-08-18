package com.gestiontareas.presentacion.controller;

import com.gestiontareas.presentacion.dto.TareaDto;
import com.gestiontareas.service.interfaces.ITareaService;
import com.gestiontareas.utils.response.ResponseCreated;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Tarea", description = "Gestion de tareas")
@RestController
@RequestMapping("/api/v1/tarea")
public class TareaController {

    @Autowired
    private ITareaService TareaService;

    // all Tarea
    @Operation(summary = "Listar tareas", description = "Obtiene todas las tareas registradas")
    @GetMapping(value = "all")
    public ResponseEntity<List<TareaDto>> findTarea(){
        return new ResponseEntity<>(this.TareaService.findAll(), HttpStatus.OK);
    }

    // one Tarea
    @Operation(summary = "Obtener tarea por id", description = "Obtiene una tarea a partir de su identificador")
    @GetMapping(value = "one/{id}")
    public ResponseEntity<TareaDto> findByIdTarea(@Parameter(description = "Id de la tarea") @PathVariable Integer id){
        return new ResponseEntity<>(this.TareaService.findById(id), HttpStatus.OK);
    }

    // Create Tarea
    @Operation(summary = "Crear tarea", description = "Registra una nueva tarea")
    @PostMapping(value = "create")
    public ResponseEntity<ResponseCreated> createTarea(@RequestBody TareaDto TareaDto){
        return new ResponseEntity<>(this.TareaService.create(TareaDto), HttpStatus.CREATED);
    }

    // update Tarea
    @Operation(summary = "Actualizar tarea", description = "Actualiza una tarea existente a partir de su identificador")
    @PutMapping(value = "update/{id}")
    public ResponseEntity<ResponseCreated> updateTarea(@RequestBody TareaDto TareaDto, @Parameter(description = "Id de la tarea") @PathVariable Integer id){
        return new ResponseEntity<>(this.TareaService.update(TareaDto, id), HttpStatus.OK);
    }

    // delete Tarea
    @Operation(summary = "Eliminar tarea", description = "Elimina una tarea a partir de su identificador")
    @DeleteMapping(value = "delete/{id}")
    public ResponseEntity<ResponseCreated> deleteTarea(@Parameter(description = "Id de la tarea") @PathVariable Integer id){
        return new ResponseEntity<>(this.           TareaService.delete(id), HttpStatus.OK);
    }

    @Operation(summary = "Tareas por usuario", description = "Obtiene la(s) tarea(s) asociadas a un usuario")
    @GetMapping(value = "userTask/{id}")
    public ResponseEntity<TareaDto> findByUserTask(@Parameter(description = "Id del usuario") @PathVariable Integer id){
        return new ResponseEntity<>(this.TareaService.findByUserId(id), HttpStatus.OK);
    }
}
