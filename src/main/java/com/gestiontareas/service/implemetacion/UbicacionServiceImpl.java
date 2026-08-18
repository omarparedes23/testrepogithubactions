package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.IUbicacionDAO;
import com.gestiontareas.model.entities.Ubicacion;
import com.gestiontareas.presentacion.dto.UbicacionDto;
import com.gestiontareas.service.interfaces.IUbicacionService;
import com.gestiontareas.utils.response.ResponseCreated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UbicacionServiceImpl implements IUbicacionService {

    @Autowired
    private IUbicacionDAO ubicacionDAO;

    @Override
    public List<UbicacionDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();

        return this.ubicacionDAO.findAllUbicacion()
                .stream()
                .map(entity -> modelMapper.map(entity, UbicacionDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UbicacionDto findById(Integer id) {
        Optional<Ubicacion> ubicacion = this.ubicacionDAO.findByIdUbicacion(id);

        if(ubicacion.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            Ubicacion currentUbicacion = ubicacion.get();

            return modelMapper.map(currentUbicacion, UbicacionDto.class);
        }else{
            return new UbicacionDto();
        }
    }

    @Override
    public ResponseCreated create(UbicacionDto ubicacionDto) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            Ubicacion ubicacion = modelMapper.map(ubicacionDto, Ubicacion.class);
            this.ubicacionDAO.saveUbicacion(ubicacion);

            return ResponseCreated.builder()
                    .message("Ubicacion Creado Exitosamente")
                    .build();
        }catch(Exception ex){
            throw new UnsupportedOperationException("Error al guardar el Ubicacion "+ ex.getMessage() );
        }
    }

    @Override
    public ResponseCreated update(UbicacionDto ubicacionDto, Integer id) {
        Optional<Ubicacion> ubicacion = this.ubicacionDAO.findByIdUbicacion(id);

        if(ubicacion.isPresent()){
            Ubicacion ubicacionEntity = ubicacion.get();

            ubicacionEntity.setUbicacion(ubicacionDto.getUbicacion());
            ubicacionEntity.setDescripcion(ubicacionDto.getDescripcion());

            this.ubicacionDAO.saveUbicacion(ubicacionEntity);

            return ResponseCreated.builder()
                    .message("Ubicacion Actualizado Exitosamente")
                    .build();
        }
        else{
            return ResponseCreated.builder()
                    .message("Ubicacion no encontrado en la base de Datos")
                    .build();
        }
    }

    @Override
    public ResponseCreated delete(Integer id) {
        Optional<Ubicacion> ubicacion = this.ubicacionDAO.findByIdUbicacion(id);

        if(ubicacion.isPresent()){
            this.ubicacionDAO.deleteUbicacion(id);
            return ResponseCreated.builder()
                    .message("Ubicacion con ID -> "+ id +" <- Eliminado Exitosamente")
                    .build();
        }else{
            return ResponseCreated.builder()
                    .message("Ubicacion con ID -> "+ id +" <- No Encontrado")
                    .build();
        }
    }
}
