package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.IComentarioDAO;
import com.gestiontareas.model.entities.Comentario;
import com.gestiontareas.presentacion.dto.ComentarioDto;
import com.gestiontareas.service.interfaces.IComentarioService;
import com.gestiontareas.utils.response.ResponseCreated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements IComentarioService {

    @Autowired
    private IComentarioDAO comentarioDAO;

    @Override
    public List<ComentarioDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();

        return this.comentarioDAO.findAllComentario()
                .stream()
                .map(entity -> modelMapper.map(entity, ComentarioDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ComentarioDto findById(Integer id) {
        Optional<Comentario> comentario = this.comentarioDAO.findByIdComentario(id);

        if(comentario.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            Comentario currentComentario = comentario.get();

            return modelMapper.map(currentComentario, ComentarioDto.class);
        }else{
            return new ComentarioDto();
        }
    }

    @Override
    public ResponseCreated create(ComentarioDto comentarioDto) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            Comentario comentario = modelMapper.map(comentarioDto, Comentario.class);
            comentario.setFechaComentario(LocalDateTime.now());
            this.comentarioDAO.saveComentario(comentario);

            return ResponseCreated.builder()
                    .message("Comentario Creado Exitosamente")
                    .build();
        }catch(Exception ex){
            throw new UnsupportedOperationException("Error al guardar el Comentario "+ ex.getMessage() );
        }
    }

    @Override
    public ResponseCreated update(ComentarioDto comentarioDto, Integer id) {
        Optional<Comentario> comentario = this.comentarioDAO.findByIdComentario(id);

        if(comentario.isPresent()){
            Comentario comentarioEntity = comentario.get();
            //comentarioEntity.setUsuario(comentarioDto.getUsuario());
            //comentarioEntity.setTarea(comentarioDto.getTarea());
            comentarioEntity.setComentario(comentarioDto.getComentario());

            this.comentarioDAO.saveComentario(comentarioEntity);

            return ResponseCreated.builder()
                    .message("Comentario Actualizado Exitosamente")
                    .build();
        }
        else{
            return ResponseCreated.builder()
                    .message("Comentario no encontrado en la base de Datos")
                    .build();
        }
    }

    @Override
    public ResponseCreated delete(Integer id) {
        Optional<Comentario> comentario = this.comentarioDAO.findByIdComentario(id);

        if(comentario.isPresent()){
            this.comentarioDAO.deleteComentario(id);
            return ResponseCreated.builder()
                    .message("Comentario con ID -> "+ id +" <- Eliminado Exitosamente")
                    .build();
        }else{
            return ResponseCreated.builder()
                    .message("Comentario con ID -> "+ id +" <- No Encontrado")
                    .build();
        }
    }
}