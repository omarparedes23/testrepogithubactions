package com.gestiontareas.model.dao;

import com.gestiontareas.model.entities.Comentario;

import java.util.List;
import java.util.Optional;

public interface IComentarioDAO {
    List<Comentario> findAllComentario();
    Optional<Comentario> findByIdComentario(Integer id);
    void saveComentario(Comentario comentario);
    void deleteComentario(Integer id);
}
