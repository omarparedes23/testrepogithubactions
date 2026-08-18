package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.IEstadoDAO;
import com.gestiontareas.model.entities.Estado;
import com.gestiontareas.presentacion.dto.EstadoDto;
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

public class EstadoServiceImplTest {

    @Mock
    private IEstadoDAO estadoDAO;

    @InjectMocks
    private EstadoServiceImpl estadoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        Estado estado1 = new Estado();
        Estado estado2 = new Estado();
        when(estadoDAO.findAllEstado()).thenReturn(Arrays.asList(estado1, estado2));

        // Act
        List<EstadoDto> result = estadoService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(estadoDAO, times(1)).findAllEstado();
    }

    @Test
    void testFindByIdWhenEstadoExists() {
        // Arrange
        Estado estado = new Estado();
        estado.setId(1);
        estado.setEstado("Pendiente");
        estado.setTipo("Interno");
        when(estadoDAO.findByIdEstado(1)).thenReturn(Optional.of(estado));

        // Act
        EstadoDto result = estadoService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Pendiente", result.getEstado());
        verify(estadoDAO, times(1)).findByIdEstado(1);
    }

    @Test
    void testFindByIdWhenEstadoDoesNotExist() {
        // Arrange
        when(estadoDAO.findByIdEstado(1)).thenReturn(Optional.empty());

        // Act
        EstadoDto result = estadoService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId());
        verify(estadoDAO, times(1)).findByIdEstado(1);
    }

    @Test
    void testCreateEstado() {
        // Arrange
        EstadoDto estadoDto = new EstadoDto();
        estadoDto.setEstado("En Proceso");
        estadoDto.setTipo("Interno");

        // Act
        ResponseCreated response = estadoService.create(estadoDto);

        // Assert
        assertEquals("Estado Creado Exitosamente", response.getMessage());
        verify(estadoDAO, times(1)).saveEstado(any(Estado.class));
    }

    @Test
    void testUpdateEstadoWhenEstadoExists() {
        // Arrange
        EstadoDto estadoDto = new EstadoDto();
        estadoDto.setEstado("Finalizado");
        estadoDto.setTipo("Externo");
        Estado existingEstado = new Estado();
        existingEstado.setId(1);

        when(estadoDAO.findByIdEstado(1)).thenReturn(Optional.of(existingEstado));

        // Act
        ResponseCreated response = estadoService.update(estadoDto, 1);

        // Assert
        assertEquals("Estado Actualizado Exitosamente", response.getMessage());
        assertEquals("Finalizado", existingEstado.getEstado());
        assertEquals("Externo", existingEstado.getTipo());
        verify(estadoDAO, times(1)).saveEstado(existingEstado);
    }

    @Test
    void testUpdateEstadoWhenEstadoDoesNotExist() {
        // Arrange
        EstadoDto estadoDto = new EstadoDto();
        when(estadoDAO.findByIdEstado(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = estadoService.update(estadoDto, 1);

        // Assert
        assertEquals("Estado no encontrado en la base de Datos", response.getMessage());
        verify(estadoDAO, times(0)).saveEstado(any(Estado.class));
    }

    @Test
    void testDeleteEstadoWhenEstadoExists() {
        // Arrange
        Estado existingEstado = new Estado();
        existingEstado.setId(1);
        when(estadoDAO.findByIdEstado(1)).thenReturn(Optional.of(existingEstado));

        // Act
        ResponseCreated response = estadoService.delete(1);

        // Assert
        assertEquals("Estado con ID -> 1 <- Eliminado Exitosamente", response.getMessage());
        verify(estadoDAO, times(1)).deleteEstado(1);
    }

    @Test
    void testDeleteEstadoWhenEstadoDoesNotExist() {
        // Arrange
        when(estadoDAO.findByIdEstado(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = estadoService.delete(1);

        // Assert
        assertEquals("Estado con ID -> 1 <- No Encontrado", response.getMessage());
        verify(estadoDAO, times(0)).deleteEstado(anyInt());
    }
}
