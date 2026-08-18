package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.ITareaDAO;
import com.gestiontareas.model.entities.Estado;
import com.gestiontareas.model.entities.Prioridad;
import com.gestiontareas.model.entities.Tarea;
import com.gestiontareas.model.entities.TipoTarea;
import com.gestiontareas.model.entities.Ubicacion;
import com.gestiontareas.model.entities.Usuario;
import com.gestiontareas.presentacion.dto.TareaDto;
import com.gestiontareas.service.interfaces.ITareaService;
import com.gestiontareas.utils.response.ResponseCreated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TareaServiceImpl implements ITareaService {

    @Autowired
    private ITareaDAO tareaDAO;

    @Override
    public List<TareaDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();

        return this.tareaDAO.findAllTarea()
                .stream()
                .map(entity -> modelMapper.map(entity, TareaDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public TareaDto findById(Integer id) {
        Optional<Tarea> tarea = this.tareaDAO.findByIdTarea(id);

        if(tarea.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            Tarea currentTarea = tarea.get();

            return modelMapper.map(currentTarea, TareaDto.class);
        }else{
            return new TareaDto();
        }
    }

    @Override
    public ResponseCreated create(TareaDto tareaDto) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            Tarea tarea = modelMapper.map(tareaDto, Tarea.class);
            tarea.setFechaCreacion(LocalDateTime.now());

            this.tareaDAO.saveTarea(tarea);

            return ResponseCreated.builder()
                    .message("Tarea Creado Exitosamente")
                    .build();
        }catch(Exception ex){
            throw new UnsupportedOperationException("Error al guardar el Tarea "+ ex.getMessage() );
        }
    }

    @Override
    public ResponseCreated update(TareaDto tareaDto, Integer id) {
        Optional<Tarea> tarea = this.tareaDAO.findByIdTarea(id);

        if(tarea.isPresent()){
            Tarea tareaEntity = tarea.get();

            tareaEntity.setNumeroTarea(tareaDto.getNumero());
            tareaEntity.setTitulo(tareaDto.getTitulo());
            tareaEntity.setDescripcion(tareaDto.getDescripcion());

            ModelMapper modelMapper = new ModelMapper();
            TipoTarea tipoTarea = modelMapper.map(tareaDto.getTipo(), TipoTarea.class);
            Ubicacion ubicacion = modelMapper.map(tareaDto.getUbicacion(), Ubicacion.class);
            Usuario usuario = modelMapper.map(tareaDto.getUsuario(), Usuario.class);
            Usuario aprobado = modelMapper.map(tareaDto.getAprobado(), Usuario.class);
            Estado estado = modelMapper.map(tareaDto.getEstado(), Estado.class);
            Prioridad prioridad = modelMapper.map(tareaDto.getPrioridad(), Prioridad.class);

            tareaEntity.setTipoTarea(tipoTarea);
            tareaEntity.setUbicacion(ubicacion);
            tareaEntity.setFechaFinalizacion(tareaDto.getFechaFinalizacion());
            tareaEntity.setRequiereCompra(tareaDto.isRequiereCompra());
            tareaEntity.setAprobado(aprobado);
            tareaEntity.setUsuario(usuario);
            tareaEntity.setEstado(estado);
            tareaEntity.setPrioridad(prioridad);

            this.tareaDAO.saveTarea(tareaEntity);

            return ResponseCreated.builder()
                    .message("Tarea Actualizado Exitosamente")
                    .build();
        }
        else{
            return ResponseCreated.builder()
                    .message("Tarea no encontrado en la base de Datos")
                    .build();
        }
    }

    @Override
    public ResponseCreated delete(Integer id) {
        Optional<Tarea> tarea = this.tareaDAO.findByIdTarea(id);

        if(tarea.isPresent()){
            this.tareaDAO.deleteTarea(id);
            return ResponseCreated.builder()
                    .message("Tarea con ID -> "+ id +" <- Eliminado Exitosamente")
                    .build();
        }else{
            return ResponseCreated.builder()
                    .message("Tarea con ID -> "+ id +" <- No Encontrado")
                    .build();
        }
    }

    @Override
    public TareaDto findByUserId(Integer id) {
        Optional<Tarea> tarea = this.tareaDAO.findByUsuarioId(id);

        if(tarea.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            Tarea currentTarea = tarea.get();

            return modelMapper.map(currentTarea, TareaDto.class);
        }else{
            return new TareaDto();
        }
    }
}
