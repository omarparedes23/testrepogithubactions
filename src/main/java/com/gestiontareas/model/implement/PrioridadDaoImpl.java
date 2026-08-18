package com.gestiontareas.model.implement;

import com.gestiontareas.model.dao.IPrioridadDAO;
import com.gestiontareas.model.entities.Prioridad;
import com.gestiontareas.model.repository.PrioridadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PrioridadDaoImpl implements IPrioridadDAO {

    @Autowired
    private PrioridadRepository prioridadRepository;

    @Override
    public List<Prioridad> findAllPrioridad() {
        return (List<Prioridad>) prioridadRepository.findAll();
    }

    @Override
    public Optional<Prioridad> findByIdPrioridad(Integer id) {
        return prioridadRepository.findById(id);
    }

    @Override
    public void savePrioridad(Prioridad prioridad) {
        this.prioridadRepository.save(prioridad);
    }

    @Override
    public void deletePrioridad(Integer id) {
        this.prioridadRepository.deleteById(id);
    }
}
