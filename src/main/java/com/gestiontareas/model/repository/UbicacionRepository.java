package com.gestiontareas.model.repository;

import org.springframework.data.repository.CrudRepository;
import com.gestiontareas.model.entities.Ubicacion;
import org.springframework.stereotype.Repository;

@Repository
public interface UbicacionRepository extends CrudRepository<Ubicacion, Integer> {
}
