package com.gestiontareas.model.repository;

import com.gestiontareas.model.entities.Estado;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstadoRepository extends CrudRepository<Estado, Integer> {
 
}
