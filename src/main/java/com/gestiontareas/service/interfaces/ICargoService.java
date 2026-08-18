package com.gestiontareas.service.interfaces;
import com.gestiontareas.presentacion.dto.CargoDto;
import com.gestiontareas.utils.response.ResponseCreated;

import java.util.List;

public interface ICargoService {

    List<CargoDto> findAll();
    CargoDto findById(Integer id);
    ResponseCreated create(CargoDto cargoDto);
    ResponseCreated update(CargoDto cargoDto, Integer id);
    ResponseCreated delete (Integer id);
}
