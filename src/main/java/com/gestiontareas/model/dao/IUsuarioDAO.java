package com.gestiontareas.model.dao;

import com.gestiontareas.model.entities.Usuario;

import java.util.List;
import java.util.Optional;

public interface IUsuarioDAO {

    List<Usuario> findAllUser();
    Optional<Usuario> findByIdUser(Integer id);
    void saveUser(Usuario usuario);
//    void updateUser(Usuario usuario);
    void deleteUser(Integer id);


}
