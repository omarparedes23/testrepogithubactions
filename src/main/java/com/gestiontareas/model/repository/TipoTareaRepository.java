package com.gestiontareas.model.repository;

import com.gestiontareas.model.entities.TipoTarea;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoTareaRepository extends CrudRepository<TipoTarea, Integer> {
}
