package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.IPrioridadDAO;
import com.gestiontareas.model.entities.Prioridad;
import com.gestiontareas.presentacion.dto.PrioridadDto;
import com.gestiontareas.service.interfaces.IPrioridadService;
import com.gestiontareas.utils.response.ResponseCreated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PrioridadServiceImpl implements IPrioridadService {

    @Autowired
    private IPrioridadDAO prioridadDAO;

    @Override
    public List<PrioridadDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();

        return this.prioridadDAO.findAllPrioridad()
                .stream()
                .map(entity -> modelMapper.map(entity, PrioridadDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public PrioridadDto findById(Integer id) {
        Optional<Prioridad> prioridad = this.prioridadDAO.findByIdPrioridad(id);

        if(prioridad.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            Prioridad currentPrioridad = prioridad.get();

            return modelMapper.map(currentPrioridad, PrioridadDto.class);
        }else{
            return new PrioridadDto();
        }
    }

    @Override
    public ResponseCreated create(PrioridadDto prioridadDto) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            Prioridad prioridad = modelMapper.map(prioridadDto, Prioridad.class);
            this.prioridadDAO.savePrioridad(prioridad);

            return ResponseCreated.builder()
                    .message("Prioridad Creado Exitosamente")
                    .build();
        }catch(Exception ex){
            throw new UnsupportedOperationException("Error al guardar el Prioridad "+ ex.getMessage() );
        }
    }

    @Override
    public ResponseCreated update(PrioridadDto prioridadDto, Integer id) {
        Optional<Prioridad> prioridad = this.prioridadDAO.findByIdPrioridad(id);

        if(prioridad.isPresent()){
            Prioridad prioridadEntity = prioridad.get();

            prioridadEntity.setPrioridadNombre(prioridadDto.getPrioridad());

            this.prioridadDAO.savePrioridad(prioridadEntity);

            return ResponseCreated.builder()
                    .message("Prioridad Actualizado Exitosamente")
                    .build();
        }
        else{
            return ResponseCreated.builder()
                    .message("Prioridad no encontrado en la base de Datos")
                    .build();
        }
    }

    @Override
    public ResponseCreated delete(Integer id) {
        Optional<Prioridad> prioridad = this.prioridadDAO.findByIdPrioridad(id);

        if(prioridad.isPresent()){
            this.prioridadDAO.deletePrioridad(id);
            return ResponseCreated.builder()
                    .message("Prioridad con ID -> "+ id +" <- Eliminado Exitosamente")
                    .build();
        }else{
            return ResponseCreated.builder()
                    .message("Prioridad con ID -> "+ id +" <- No Encontrado")
                    .build();
        }
    }
}
