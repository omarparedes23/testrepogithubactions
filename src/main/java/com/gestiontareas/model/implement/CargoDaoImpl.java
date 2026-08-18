package com.gestiontareas.model.implement;

import com.gestiontareas.model.dao.ICargoDAO;
import com.gestiontareas.model.entities.Cargo;
import com.gestiontareas.model.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CargoDaoImpl implements ICargoDAO {

    @Autowired
    private CargoRepository cargoRepository;

    @Override
    public List<Cargo> findAllCargo() {
        return (List<Cargo>)cargoRepository.findAll();
    }

    @Override
    public Optional<Cargo> findByIdCargo(Integer id) {
        return cargoRepository.findById(id);
    }

    @Override
    public void saveCargo(Cargo cargo) {
       cargoRepository.save(cargo);
    }

    @Override
    public void deleteCargo(Integer id) {
        cargoRepository.deleteById(id);
    }
}
