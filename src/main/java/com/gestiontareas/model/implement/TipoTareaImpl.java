package com.gestiontareas.model.implement;

import com.gestiontareas.model.dao.ITipoTareaDAO;
import com.gestiontareas.model.entities.TipoTarea;
import com.gestiontareas.model.repository.TipoTareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TipoTareaImpl implements ITipoTareaDAO {

    @Autowired
    private TipoTareaRepository tipoTareaRepository;

    @Override
    public List<TipoTarea> findAllTipoTarea() {
        return (List<TipoTarea>) this.tipoTareaRepository.findAll();
    }

    @Override
    public Optional<TipoTarea> findByIdTipoTarea(Integer id) {
        return this.tipoTareaRepository.findById(id);
    }

    @Override
    public void saveTipoTarea(TipoTarea tipoTarea) {
        this.tipoTareaRepository.save(tipoTarea);
    }

    @Override
    public void deleteTipoTarea(Integer id) {
        this.tipoTareaRepository.deleteById(id);
    }
}
