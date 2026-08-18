package com.gestiontareas.model.dao;

import com.gestiontareas.model.entities.Tarea;

import java.util.List;
import java.util.Optional;

public interface ITareaDAO {

    List<Tarea> findAllTarea();
    Optional<Tarea> findByIdTarea(Integer id);
    void saveTarea(Tarea tarea);
    void deleteTarea(Integer id);
    Optional<Tarea> findByUsuarioId(Integer id);
}
