package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.IPrioridadDAO;
import com.gestiontareas.model.entities.Prioridad;
import com.gestiontareas.presentacion.dto.PrioridadDto;
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

public class PrioridadServiceImplTest {

    @Mock
    private IPrioridadDAO prioridadDAO;

    @InjectMocks
    private PrioridadServiceImpl prioridadService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        Prioridad prioridad1 = new Prioridad("Alta");
        Prioridad prioridad2 = new Prioridad("Baja");
        when(prioridadDAO.findAllPrioridad()).thenReturn(Arrays.asList(prioridad1, prioridad2));

        // Act
        List<PrioridadDto> result = prioridadService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(prioridadDAO, times(1)).findAllPrioridad();
    }

    @Test
    void testFindByIdWhenPrioridadExists() {
        // Arrange
        Prioridad prioridad = new Prioridad("Alta");
        prioridad.setId(1);
        when(prioridadDAO.findByIdPrioridad(1)).thenReturn(Optional.of(prioridad));

        // Act
        PrioridadDto result = prioridadService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Alta", result.getPrioridad());
        verify(prioridadDAO, times(1)).findByIdPrioridad(1);
    }

    @Test
    void testFindByIdWhenPrioridadDoesNotExist() {
        // Arrange
        when(prioridadDAO.findByIdPrioridad(1)).thenReturn(Optional.empty());

        // Act
        PrioridadDto result = prioridadService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId());
        verify(prioridadDAO, times(1)).findByIdPrioridad(1);
    }

    @Test
    void testCreatePrioridad() {
        // Arrange
        PrioridadDto prioridadDto = new PrioridadDto();
        prioridadDto.setPrioridad("Media");

        // Act
        ResponseCreated response = prioridadService.create(prioridadDto);

        // Assert
        assertEquals("Prioridad Creado Exitosamente", response.getMessage());
        verify(prioridadDAO, times(1)).savePrioridad(any(Prioridad.class));
    }

    @Test
    void testUpdatePrioridadWhenPrioridadExists() {
        // Arrange
        PrioridadDto prioridadDto = new PrioridadDto();
        prioridadDto.setPrioridad("Urgente");
        Prioridad existingPrioridad = new Prioridad("Baja");
        existingPrioridad.setId(1);

        when(prioridadDAO.findByIdPrioridad(1)).thenReturn(Optional.of(existingPrioridad));

        // Act
        ResponseCreated response = prioridadService.update(prioridadDto, 1);

        // Assert
        assertEquals("Prioridad Actualizado Exitosamente", response.getMessage());
        assertEquals("Urgente", existingPrioridad.getPrioridadNombre());
        verify(prioridadDAO, times(1)).savePrioridad(existingPrioridad);
    }

    @Test
    void testUpdatePrioridadWhenPrioridadDoesNotExist() {
        // Arrange
        PrioridadDto prioridadDto = new PrioridadDto();
        when(prioridadDAO.findByIdPrioridad(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = prioridadService.update(prioridadDto, 1);

        // Assert
        assertEquals("Prioridad no encontrado en la base de Datos", response.getMessage());
        verify(prioridadDAO, times(0)).savePrioridad(any(Prioridad.class));
    }

    @Test
    void testDeletePrioridadWhenPrioridadExists() {
        // Arrange
        Prioridad existingPrioridad = new Prioridad("Baja");
        existingPrioridad.setId(1);
        when(prioridadDAO.findByIdPrioridad(1)).thenReturn(Optional.of(existingPrioridad));

        // Act
        ResponseCreated response = prioridadService.delete(1);

        // Assert
        assertEquals("Prioridad con ID -> 1 <- Eliminado Exitosamente", response.getMessage());
        verify(prioridadDAO, times(1)).deletePrioridad(1);
    }

    @Test
    void testDeletePrioridadWhenPrioridadDoesNotExist() {
        // Arrange
        when(prioridadDAO.findByIdPrioridad(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = prioridadService.delete(1);

        // Assert
        assertEquals("Prioridad con ID -> 1 <- No Encontrado", response.getMessage());
        verify(prioridadDAO, times(0)).deletePrioridad(anyInt());
    }
}
