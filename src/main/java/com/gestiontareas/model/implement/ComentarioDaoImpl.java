package com.gestiontareas.model.implement;

import com.gestiontareas.model.dao.IComentarioDAO;
import com.gestiontareas.model.entities.Comentario;
import com.gestiontareas.model.repository.ComentarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ComentarioDaoImpl implements IComentarioDAO {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Override
    public List<Comentario> findAllComentario() {
        return (List<Comentario>) comentarioRepository.findAll();
    }

    @Override
    public Optional<Comentario> findByIdComentario(Integer id) {
        return comentarioRepository.findById(id);
    }

    @Override
    public void saveComentario(Comentario comentario) {
        comentarioRepository.save(comentario);
    }

    @Override
    public void deleteComentario(Integer id) {
        comentarioRepository.deleteById(id);
    }
}