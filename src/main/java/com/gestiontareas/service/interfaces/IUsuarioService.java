package com.gestiontareas.service.interfaces;

import com.gestiontareas.presentacion.dto.UsuarioDto;
import com.gestiontareas.utils.response.ResponseCreated;

import java.util.List;

public interface IUsuarioService {

    List<UsuarioDto> findAll();
    UsuarioDto findById(Integer id);
    ResponseCreated createUser(UsuarioDto usuarioDto);
    ResponseCreated updateUser(UsuarioDto usuarioDto, Integer id);
    ResponseCreated deleteUser (Integer id);

}
