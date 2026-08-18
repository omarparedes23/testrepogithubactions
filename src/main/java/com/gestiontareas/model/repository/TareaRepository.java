package com.gestiontareas.model.repository;

import com.gestiontareas.model.entities.Tarea;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TareaRepository extends CrudRepository<Tarea, Integer> {

    Optional<Tarea> findByUsuarioId(Integer integer);
}