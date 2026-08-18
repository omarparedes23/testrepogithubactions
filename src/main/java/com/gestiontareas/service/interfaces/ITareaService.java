package com.gestiontareas.service.interfaces;

import com.gestiontareas.presentacion.dto.TareaDto;
import com.gestiontareas.utils.response.ResponseCreated;

import java.util.List;

public interface ITareaService {

    List<TareaDto> findAll();
    TareaDto findById(Integer id);
    ResponseCreated create(TareaDto tareaDto);
    ResponseCreated update(TareaDto tareaDto, Integer id);
    ResponseCreated delete(Integer id);
    TareaDto findByUserId(Integer id);


}
