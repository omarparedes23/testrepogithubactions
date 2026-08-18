package com.gestiontareas.model.dao;
import com.gestiontareas.model.entities.Ubicacion;

import java.util.List;
import java.util.Optional;

public interface IUbicacionDAO {
    List<Ubicacion> findAllUbicacion();
    Optional<Ubicacion> findByIdUbicacion(Integer id);
    void saveUbicacion(Ubicacion ubicacion);
    void deleteUbicacion(Integer id);
}
