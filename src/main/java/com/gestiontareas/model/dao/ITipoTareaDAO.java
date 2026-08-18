package com.gestiontareas.model.dao;

import com.gestiontareas.model.entities.TipoTarea;

import java.util.List;
import java.util.Optional;

public interface ITipoTareaDAO {
    List<TipoTarea> findAllTipoTarea();
    Optional<TipoTarea> findByIdTipoTarea(Integer id);
    void saveTipoTarea(TipoTarea tipoTarea);
    void deleteTipoTarea(Integer id);

}
