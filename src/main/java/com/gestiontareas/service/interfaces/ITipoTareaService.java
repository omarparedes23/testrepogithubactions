package com.gestiontareas.service.interfaces;

import com.gestiontareas.presentacion.dto.TipoTareaDto;
import com.gestiontareas.utils.response.ResponseCreated;

import java.util.List;

public interface ITipoTareaService {

    List<TipoTareaDto> findAll();
    TipoTareaDto findById(Integer id);
    ResponseCreated create(TipoTareaDto tipoTareaDto);
    ResponseCreated update(TipoTareaDto tipoTareaDto, Integer id);
    ResponseCreated delete (Integer id);

}
