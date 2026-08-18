package com.gestiontareas.model.dao;

import com.gestiontareas.model.entities.Cargo;

import java.util.List;
import java.util.Optional;

public interface ICargoDAO {

    List<Cargo> findAllCargo();
    Optional<Cargo> findByIdCargo(Integer id);
    void saveCargo(Cargo cargo);
    void deleteCargo(Integer id);


}
