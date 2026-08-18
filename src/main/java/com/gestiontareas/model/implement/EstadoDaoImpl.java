package com.gestiontareas.model.implement;

import com.gestiontareas.model.dao.IEstadoDAO;
import com.gestiontareas.model.entities.Estado;
import com.gestiontareas.model.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EstadoDaoImpl implements IEstadoDAO {

    @Autowired
    private EstadoRepository estadoRepository;

    @Override
    public List<Estado> findAllEstado() {
        return (List<Estado>) estadoRepository.findAll();
    }

    @Override
    public Optional<Estado> findByIdEstado(Integer id) {
        return estadoRepository.findById(id);
    }

    @Override
    public void saveEstado(Estado estado) {
        estadoRepository.save(estado);
    }

    @Override
    public void deleteEstado(Integer id) {
        estadoRepository.deleteById(id);
    }
}