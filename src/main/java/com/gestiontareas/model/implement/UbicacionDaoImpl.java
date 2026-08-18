package com.gestiontareas.model.implement;

import com.gestiontareas.model.dao.IUbicacionDAO;
import com.gestiontareas.model.entities.Ubicacion;
import com.gestiontareas.model.repository.UbicacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


@Component
public class UbicacionDaoImpl implements IUbicacionDAO {

    @Autowired
    private UbicacionRepository ubicacionRepository;

    @Override
    public List<Ubicacion> findAllUbicacion() {
        return (List<Ubicacion>) this.ubicacionRepository.findAll();
    }

    @Override
    public Optional<Ubicacion> findByIdUbicacion(Integer id) {
        return this.ubicacionRepository.findById(id);
    }

    @Override
    public void saveUbicacion(Ubicacion ubicacion) {
        this.ubicacionRepository.save(ubicacion);
    }

    @Override
    public void deleteUbicacion(Integer id) {
        this.ubicacionRepository.deleteById(id);
    }
}
