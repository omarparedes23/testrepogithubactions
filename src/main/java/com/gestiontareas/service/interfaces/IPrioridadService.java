package com.gestiontareas.service.interfaces;

import com.gestiontareas.presentacion.dto.PrioridadDto;
import com.gestiontareas.utils.response.ResponseCreated;

import java.util.List;

public interface IPrioridadService {
    List<PrioridadDto> findAll();
    PrioridadDto findById(Integer id);
    ResponseCreated create(PrioridadDto prioridadDto);
    ResponseCreated update(PrioridadDto prioridadDto, Integer id);
    ResponseCreated delete (Integer id);
}
