package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.IUbicacionDAO;
import com.gestiontareas.model.entities.Ubicacion;
import com.gestiontareas.presentacion.dto.UbicacionDto;
import com.gestiontareas.utils.response.ResponseCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UbicacionServiceImplTest {

    @Mock
    private IUbicacionDAO ubicacionDAO;

    @InjectMocks
    private UbicacionServiceImpl ubicacionService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        Ubicacion ubicacion1 = new Ubicacion("Almacen", "Zona norte");
        Ubicacion ubicacion2 = new Ubicacion("Oficina", "Piso 2");
        when(ubicacionDAO.findAllUbicacion()).thenReturn(Arrays.asList(ubicacion1, ubicacion2));

        // Act
        List<UbicacionDto> result = ubicacionService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(ubicacionDAO, times(1)).findAllUbicacion();
    }

    @Test
    void testFindByIdWhenUbicacionExists() {
        // Arrange
        Ubicacion ubicacion = new Ubicacion("Almacen", "Zona norte");
        when(ubicacionDAO.findByIdUbicacion(1)).thenReturn(Optional.of(ubicacion));

        // Act
        UbicacionDto result = ubicacionService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals("Almacen", result.getUbicacion());
        assertEquals("Zona norte", result.getDescripcion());
        verify(ubicacionDAO, times(1)).findByIdUbicacion(1);
    }

    @Test
    void testFindByIdWhenUbicacionDoesNotExist() {
        // Arrange
        when(ubicacionDAO.findByIdUbicacion(1)).thenReturn(Optional.empty());

        // Act
        UbicacionDto result = ubicacionService.findById(1);

        // Assert
        assertNotNull(result);
        assertNull(result.getUbicacion());
        verify(ubicacionDAO, times(1)).findByIdUbicacion(1);
    }

    @Test
    void testCreateUbicacion() {
        // Arrange
        UbicacionDto ubicacionDto = new UbicacionDto();
        ubicacionDto.setUbicacion("Taller");
        ubicacionDto.setDescripcion("Zona sur");

        // Act
        ResponseCreated response = ubicacionService.create(ubicacionDto);

        // Assert
        assertEquals("Ubicacion Creado Exitosamente", response.getMessage());
        verify(ubicacionDAO, times(1)).saveUbicacion(any(Ubicacion.class));
    }

    @Test
    void testUpdateUbicacionWhenUbicacionExists() {
        // Arrange
        UbicacionDto ubicacionDto = new UbicacionDto();
        ubicacionDto.setUbicacion("Patio");
        ubicacionDto.setDescripcion("Zona este");
        Ubicacion existingUbicacion = new Ubicacion("Almacen", "Zona norte");
        existingUbicacion.setId(1);

        when(ubicacionDAO.findByIdUbicacion(1)).thenReturn(Optional.of(existingUbicacion));

        // Act
        ResponseCreated response = ubicacionService.update(ubicacionDto, 1);

        // Assert
        assertEquals("Ubicacion Actualizado Exitosamente", response.getMessage());
        assertEquals("Patio", existingUbicacion.getUbicacion());
        assertEquals("Zona este", existingUbicacion.getDescripcion());
        verify(ubicacionDAO, times(1)).saveUbicacion(existingUbicacion);
    }

    @Test
    void testUpdateUbicacionWhenUbicacionDoesNotExist() {
        // Arrange
        UbicacionDto ubicacionDto = new UbicacionDto();
        when(ubicacionDAO.findByIdUbicacion(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = ubicacionService.update(ubicacionDto, 1);

        // Assert
        assertEquals("Ubicacion no encontrado en la base de Datos", response.getMessage());
        verify(ubicacionDAO, times(0)).saveUbicacion(any(Ubicacion.class));
    }

    @Test
    void testDeleteUbicacionWhenUbicacionExists() {
        // Arrange
        Ubicacion existingUbicacion = new Ubicacion("Almacen", "Zona norte");
        existingUbicacion.setId(1);
        when(ubicacionDAO.findByIdUbicacion(1)).thenReturn(Optional.of(existingUbicacion));

        // Act
        ResponseCreated response = ubicacionService.delete(1);

        // Assert
        assertEquals("Ubicacion con ID -> 1 <- Eliminado Exitosamente", response.getMessage());
        verify(ubicacionDAO, times(1)).deleteUbicacion(1);
    }

    @Test
    void testDeleteUbicacionWhenUbicacionDoesNotExist() {
        // Arrange
        when(ubicacionDAO.findByIdUbicacion(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = ubicacionService.delete(1);

        // Assert
        assertEquals("Ubicacion con ID -> 1 <- No Encontrado", response.getMessage());
        verify(ubicacionDAO, times(0)).deleteUbicacion(anyInt());
    }
}
