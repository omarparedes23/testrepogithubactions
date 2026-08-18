package com.gestiontareas.model.dao;

import com.gestiontareas.model.entities.Estado;

import java.util.List;
import java.util.Optional;

public interface IEstadoDAO {
    List<Estado> findAllEstado();
    Optional<Estado> findByIdEstado(Integer id);
    void saveEstado(Estado estado);
    void deleteEstado(Integer id);

}
