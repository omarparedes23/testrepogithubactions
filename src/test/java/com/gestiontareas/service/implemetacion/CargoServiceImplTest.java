package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.ICargoDAO;
import com.gestiontareas.model.entities.Cargo;
import com.gestiontareas.model.entities.CargoEnum;
import com.gestiontareas.presentacion.dto.CargoDto;
import com.gestiontareas.utils.response.ResponseCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CargoServiceImplTest {

        @Mock
        private ICargoDAO cargoDAO;

        @InjectMocks
        private CargoServiceImpl cargoService;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        void testFindAll() {
            // Arrange
            Cargo cargo1 = new Cargo();
            Cargo cargo2 = new Cargo();
            when(cargoDAO.findAllCargo()).thenReturn(Arrays.asList(cargo1, cargo2));

            // Act
            List<CargoDto> result = cargoService.findAll();

            // Assert
            assertEquals(2, result.size());
            
            verify(cargoDAO, times(1)).findAllCargo();
        }

        @Test
        void testFindByIdWhenCargoExists() {
            // Arrange
            Cargo cargo = new Cargo();
            cargo.setId(1);
            when(cargoDAO.findByIdCargo(1)).thenReturn(Optional.of(cargo));

            // Act
            CargoDto result = cargoService.findById(1);

            // Assert
            assertNotNull(result);
            assertEquals(1, result.getId());
            verify(cargoDAO, times(1)).findByIdCargo(1);
        }

        @Test
        void testFindByIdWhenCargoDoesNotExist() {
            // Arrange
            when(cargoDAO.findByIdCargo(1)).thenReturn(Optional.empty());

            // Act
            CargoDto result = cargoService.findById(1);

            // Assert
            assertNotNull(result);
            assertEquals(0, result.getId()); // Verifica que el ID sea 0 en lugar de null
            verify(cargoDAO, times(1)).findByIdCargo(1);
        }

        @Test
        void testCreateCargo() {
            // Arrange
            CargoDto cargoDto = new CargoDto();
            cargoDto.setCargo(CargoEnum.ADMIN);
            Cargo cargo = new ModelMapper().map(cargoDto, Cargo.class);

            // Act
            ResponseCreated response = cargoService.create(cargoDto);

            // Assert
            assertEquals("Cargo Creado Exitosamente", response.getMessage());
            verify(cargoDAO, times(1)).saveCargo(any(Cargo.class));
        }

        @Test
        void testUpdateCargoWhenCargoExists() {
            // Arrange
            CargoDto cargoDto = new CargoDto();
            cargoDto.setCargo(CargoEnum.LIMPIEZA);
            Cargo existingCargo = new Cargo();
            existingCargo.setId(1);

            when(cargoDAO.findByIdCargo(1)).thenReturn(Optional.of(existingCargo));

            // Act
            ResponseCreated response = cargoService.update(cargoDto, 1);

            // Assert
            assertEquals("Cargo Actualizado Exitosamente", response.getMessage());
            verify(cargoDAO, times(1)).saveCargo(existingCargo);
        }

        @Test
        void testUpdateCargoWhenCargoDoesNotExist() {
            // Arrange
            CargoDto cargoDto = new CargoDto();
            when(cargoDAO.findByIdCargo(1)).thenReturn(Optional.empty());

            // Act
            ResponseCreated response = cargoService.update(cargoDto, 1);

            // Assert
            assertEquals("Cargo no encontrado en la base de Datos", response.getMessage());
            verify(cargoDAO, times(0)).saveCargo(any(Cargo.class));
        }

        @Test
        void testDeleteCargoWhenCargoExists() {
            // Arrange
            Cargo existingCargo = new Cargo();
            existingCargo.setId(1);
            when(cargoDAO.findByIdCargo(1)).thenReturn(Optional.of(existingCargo));

            // Act
            ResponseCreated response = cargoService.delete(1);

            // Assert
            assertEquals("Cargo con ID -> 1 <- Eliminado Exitosamente", response.getMessage());
            verify(cargoDAO, times(1)).deleteCargo(1);
        }

        @Test
        void testDeleteCargoWhenCargoDoesNotExist() {
            // Arrange
            when(cargoDAO.findByIdCargo(1)).thenReturn(Optional.empty());

            // Act
            ResponseCreated response = cargoService.delete(1);

            // Assert
            assertEquals("Cargo con ID -> 1 <- No Encontrado", response.getMessage());
            verify(cargoDAO, times(0)).deleteCargo(anyInt());
        }
    }
