package com.gestiontareas.model.dao;

import com.gestiontareas.model.entities.Prioridad;

import java.util.List;
import java.util.Optional;

public interface IPrioridadDAO {
    List<Prioridad> findAllPrioridad();
    Optional<Prioridad> findByIdPrioridad(Integer id);
    void savePrioridad(Prioridad prioridad);
    void deletePrioridad(Integer id);

}
