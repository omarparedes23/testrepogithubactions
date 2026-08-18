package com.gestiontareas.model.repository;

import com.gestiontareas.model.entities.Prioridad;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrioridadRepository extends CrudRepository<Prioridad, Integer> {
}
