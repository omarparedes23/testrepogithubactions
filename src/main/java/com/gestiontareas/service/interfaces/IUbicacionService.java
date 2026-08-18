package com.gestiontareas.service.interfaces;

import com.gestiontareas.presentacion.dto.UbicacionDto;
import com.gestiontareas.utils.response.ResponseCreated;

import java.util.List;

public interface IUbicacionService {
    List<UbicacionDto> findAll();
    UbicacionDto findById(Integer id);
    ResponseCreated create(UbicacionDto ubicacionDto);
    ResponseCreated update(UbicacionDto ubicacionDto, Integer id);
    ResponseCreated delete (Integer id);
}
