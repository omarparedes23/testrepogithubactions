package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.ITipoTareaDAO;
import com.gestiontareas.model.entities.TipoTarea;
import com.gestiontareas.presentacion.dto.TipoTareaDto;
import com.gestiontareas.service.interfaces.ITipoTareaService;
import com.gestiontareas.utils.response.ResponseCreated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class TipoTareaServiceImpl implements ITipoTareaService {


    @Autowired
    private ITipoTareaDAO tipoTareaDAO;

    @Override
    public List<TipoTareaDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();

        return this.tipoTareaDAO.findAllTipoTarea()
                .stream()
                .map(entity -> modelMapper.map(entity, TipoTareaDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TipoTareaDto findById(Integer id) {
        Optional<TipoTarea> tipoTarea = this.tipoTareaDAO.findByIdTipoTarea(id);

        if(tipoTarea.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            TipoTarea currentTipoTarea = tipoTarea.get();

            return modelMapper.map(currentTipoTarea, TipoTareaDto.class);
        }else{
            return new TipoTareaDto();
        }
    }

    @Override
    public ResponseCreated create(TipoTareaDto tipoTareaDto) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            TipoTarea tipoTarea = modelMapper.map(tipoTareaDto, TipoTarea.class);
            this.tipoTareaDAO.saveTipoTarea(tipoTarea);

            return ResponseCreated.builder()
                    .message("TipoTarea Creado Exitosamente")
                    .build();
        }catch(Exception ex){
            throw new UnsupportedOperationException("Error al guardar el TipoTarea "+ ex.getMessage() );
        }
    }

    @Override
    public ResponseCreated update(TipoTareaDto tipoTareaDto, Integer id) {
        Optional<TipoTarea> tipoTarea = this.tipoTareaDAO.findByIdTipoTarea(id);

        if(tipoTarea.isPresent()){
            TipoTarea tipoTareaEntity = tipoTarea.get();

            tipoTareaEntity.setTipoTareaNombre(tipoTareaDto.getTipo());
            tipoTareaEntity.setImagen(tipoTareaDto.getImagen());

            this.tipoTareaDAO.saveTipoTarea(tipoTareaEntity);

            return ResponseCreated.builder()
                    .message("TipoTarea Actualizado Exitosamente")
                    .build();
        }
        else{
            return ResponseCreated.builder()
                    .message("TipoTarea no encontrado en la base de Datos")
                    .build();
        }
    }

    @Override
    public ResponseCreated delete(Integer id) {
        Optional<TipoTarea> usuario = this.tipoTareaDAO.findByIdTipoTarea(id);

        if(usuario.isPresent()){
            this.tipoTareaDAO.deleteTipoTarea(id);
            return ResponseCreated.builder()
                    .message("TipoTarea con ID -> "+ id +" <- Eliminado Exitosamente")
                    .build();
        }else{
            return ResponseCreated.builder()
                    .message("TipoTarea con ID -> "+ id +" <- No Encontrado")
                    .build();
        }
    }
}
