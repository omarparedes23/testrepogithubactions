package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.ICargoDAO;
import com.gestiontareas.model.entities.Cargo;
import com.gestiontareas.presentacion.dto.CargoDto;
import com.gestiontareas.service.interfaces.ICargoService;
import com.gestiontareas.utils.response.ResponseCreated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CargoServiceImpl implements ICargoService {

    @Autowired
    private ICargoDAO cargoDAO;

    @Override
    public List<CargoDto> findAll() {
        ModelMapper modelMapper = new ModelMapper();

        return this.cargoDAO.findAllCargo()
                .stream()
                .map(entity -> modelMapper.map(entity, CargoDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public CargoDto findById(Integer id) {
        Optional<Cargo> cargo = this.cargoDAO.findByIdCargo(id);

        if(cargo.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            Cargo currentCargo = cargo.get();

            return modelMapper.map(currentCargo, CargoDto.class);
        }else{
            return new CargoDto();
        }
    }

    @Override
    public ResponseCreated create(CargoDto cargoDto) {
        try{
            ModelMapper modelMapper = new ModelMapper();
            Cargo cargo = modelMapper.map(cargoDto, Cargo.class);
            this.cargoDAO.saveCargo(cargo);

            return ResponseCreated.builder()
                    .message("Cargo Creado Exitosamente")
                    .build();
        }catch(Exception ex){
            throw new UnsupportedOperationException("Error al guardar el Cargo "+ ex.getMessage() );
        }
    }

    @Override
    public ResponseCreated update(CargoDto cargoDto, Integer id) {
        Optional<Cargo> cargo = this.cargoDAO.findByIdCargo(id);

        if(cargo.isPresent()){
            Cargo cargoEntity = cargo.get();

            cargoEntity.setCargoNombre(cargoDto.getCargo());

            this.cargoDAO.saveCargo(cargoEntity);

            return ResponseCreated.builder()
                    .message("Cargo Actualizado Exitosamente")
                    .build();
        }
        else{
            return ResponseCreated.builder()
                    .message("Cargo no encontrado en la base de Datos")
                    .build();
        }
    }

    @Override
    public ResponseCreated delete(Integer id) {
        Optional<Cargo> cargo = this.cargoDAO.findByIdCargo(id);

        if(cargo.isPresent()){
            this.cargoDAO.deleteCargo(id);
            return ResponseCreated.builder()
                    .message("Cargo con ID -> "+ id +" <- Eliminado Exitosamente")
                    .build();
        }else{
            return ResponseCreated.builder()
                    .message("Cargo con ID -> "+ id +" <- No Encontrado")
                    .build();
        }
    }
}

