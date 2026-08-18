package com.gestiontareas.model.implement;

import com.gestiontareas.model.dao.IUsuarioDAO;
import com.gestiontareas.model.entities.Usuario;
import com.gestiontareas.model.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
public class UsuarioDaoImpl implements IUsuarioDAO {


     @Autowired
     private UsuarioRepository usuarioRepository;
//    @PersistenceContext
//    private EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> findAllUser() {
        return (List<Usuario>) usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> findByIdUser(Integer id) {
        return usuarioRepository.findById(id);
    }

    @Override
    @Transactional
    public void saveUser(Usuario usuario) {
        usuarioRepository.save(usuario);
    }

//    @Override
//    @Transactional
//    public void updateUser(Usuario usuario) {
//        usuarioRepository.
//    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        this.usuarioRepository.deleteById(id);
    }
}
