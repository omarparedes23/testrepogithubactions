package com.gestiontareas.service.interfaces;

import com.gestiontareas.presentacion.dto.EstadoDto;
import com.gestiontareas.utils.response.ResponseCreated;

import java.util.List;

public interface IEstadoService {
    List<EstadoDto> findAll();
    EstadoDto findById(Integer id);
    ResponseCreated create(EstadoDto estadoDto);
    ResponseCreated update(EstadoDto estadoDto, Integer id);
    ResponseCreated delete (Integer id);
}
