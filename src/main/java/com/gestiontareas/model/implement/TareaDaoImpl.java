package com.gestiontareas.model.implement;

import com.gestiontareas.model.dao.ITareaDAO;
import com.gestiontareas.model.entities.Tarea;
import com.gestiontareas.model.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TareaDaoImpl implements ITareaDAO {

    @Autowired
    private TareaRepository tareaRepository;

    @Override
    public List<Tarea> findAllTarea() {
        return (List<Tarea>) this.tareaRepository.findAll();
    }

    @Override
    public Optional<Tarea> findByIdTarea(Integer id) {
        return this.tareaRepository.findById(id);
    }

    @Override
    public void saveTarea(Tarea tarea) {
        this.tareaRepository.save(tarea);
    }

    @Override
    public void deleteTarea(Integer id) {
        this.tareaRepository.deleteById(id);
    }

    @Override
    public Optional<Tarea> findByUsuarioId(Integer id) {
        return this.tareaRepository.findByUsuarioId(id);
    }
}
