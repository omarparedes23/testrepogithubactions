package com.gestiontareas.model.repository;

import com.gestiontareas.model.entities.Comentario;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ComentarioRepository extends CrudRepository<Comentario, Integer> {
}
