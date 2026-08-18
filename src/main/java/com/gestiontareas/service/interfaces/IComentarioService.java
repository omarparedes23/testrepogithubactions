package com.gestiontareas.service.interfaces;

import com.gestiontareas.presentacion.dto.ComentarioDto;
import com.gestiontareas.utils.response.ResponseCreated;

import java.util.List;

public interface IComentarioService {
    List<ComentarioDto> findAll();
    ComentarioDto findById(Integer id);
    ResponseCreated create(ComentarioDto comentarioDto);
    ResponseCreated update(ComentarioDto comentarioDto, Integer id);
    ResponseCreated delete (Integer id);
}
