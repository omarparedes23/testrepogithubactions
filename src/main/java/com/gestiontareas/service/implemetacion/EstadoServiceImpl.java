package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.IEstadoDAO;
import com.gestiontareas.model.entities.Estado;
import com.gestiontareas.presentacion.dto.EstadoDto;
import com.gestiontareas.service.interfaces.IEstadoService;
import com.gestiontareas.utils.response.ResponseCreated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EstadoServiceImpl implements IEstadoService {

    @Autowired
    private IEstadoDAO estadoDAO;

    @Override
    public List<EstadoDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();

        return this.estadoDAO.findAllEstado()
                .stream()
                .map(entity -> modelMapper.map(entity, EstadoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public EstadoDto findById(Integer id) {
        Optional<Estado> estado = this.estadoDAO.findByIdEstado(id);

        if(estado.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            Estado currentEstado = estado.get();

            return modelMapper.map(currentEstado, EstadoDto.class);
        }else{
            return new EstadoDto();
        }
    }

    @Override
    public ResponseCreated create(EstadoDto estadoDto) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            Estado estado = modelMapper.map(estadoDto, Estado.class);
            this.estadoDAO.saveEstado(estado);

            return ResponseCreated.builder()
                    .message("Estado Creado Exitosamente")
                    .build();
        }catch(Exception ex){
            throw new UnsupportedOperationException("Error al guardar el Estado "+ ex.getMessage() );
        }
    }

    @Override
    public ResponseCreated update(EstadoDto estadoDto, Integer id) {
        Optional<Estado> estado = this.estadoDAO.findByIdEstado(id);

        if(estado.isPresent()){
            Estado estadoEntity = estado.get();

            estadoEntity.setEstado(estadoDto.getEstado());
            estadoEntity.setTipo(estadoDto.getTipo());

            this.estadoDAO.saveEstado(estadoEntity);

            return ResponseCreated.builder()
                    .message("Estado Actualizado Exitosamente")
                    .build();
        }
        else{
            return ResponseCreated.builder()
                    .message("Estado no encontrado en la base de Datos")
                    .build();
        }
    }

    @Override
    public ResponseCreated delete(Integer id) {
        Optional<Estado> estado = this.estadoDAO.findByIdEstado(id);

        if(estado.isPresent()){
            this.estadoDAO.deleteEstado(id);
            return ResponseCreated.builder()
                    .message("Estado con ID -> "+ id +" <- Eliminado Exitosamente")
                    .build();
        }else{
            return ResponseCreated.builder()
                    .message("Estado con ID -> "+ id +" <- No Encontrado")
                    .build();
        }
    }
}
