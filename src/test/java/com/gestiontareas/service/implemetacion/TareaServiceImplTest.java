package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.ITareaDAO;
import com.gestiontareas.model.entities.Tarea;
import com.gestiontareas.presentacion.dto.EstadoDto;
import com.gestiontareas.presentacion.dto.PrioridadDto;
import com.gestiontareas.presentacion.dto.TareaDto;
import com.gestiontareas.presentacion.dto.TipoTareaDto;
import com.gestiontareas.presentacion.dto.UbicacionDto;
import com.gestiontareas.presentacion.dto.UsuarioDto;
import com.gestiontareas.utils.response.ResponseCreated;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TareaServiceImplTest {

    @Mock
    private ITareaDAO tareaDAO;

    @InjectMocks
    private TareaServiceImpl tareaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private TareaDto buildTareaDto() {
        TareaDto dto = new TareaDto();
        dto.setTitulo("Revisar equipo");
        dto.setDescripcion("Revision preventiva del equipo de aire");
        dto.setNumero("T-001");
        dto.setRequiereCompra(false);
        dto.setFechaFinalizacion(LocalDateTime.now().plusDays(3));
        dto.setEstado(new EstadoDto(1, "Pendiente", "Interno"));
        dto.setPrioridad(new PrioridadDto(1, "Alta"));
        dto.setTipo(new TipoTareaDto(1, "Correctivo", "img.png"));
        dto.setUbicacion(new UbicacionDto("1", "Almacen", "Zona norte"));
        dto.setUsuario(new UsuarioDto());
        dto.setAprobado(new UsuarioDto());
        return dto;
    }

    @Test
    void testFindAll() {
        // Arrange
        Tarea tarea1 = new Tarea();
        Tarea tarea2 = new Tarea();
        when(tareaDAO.findAllTarea()).thenReturn(Arrays.asList(tarea1, tarea2));

        // Act
        List<TareaDto> result = tareaService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(tareaDAO, times(1)).findAllTarea();
    }

    @Test
    void testFindByIdWhenTareaExists() {
        // Arrange
        Tarea tarea = new Tarea();
        tarea.setId(1);
        tarea.setTitulo("Revisar equipo");
        when(tareaDAO.findByIdTarea(1)).thenReturn(Optional.of(tarea));

        // Act
        TareaDto result = tareaService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Revisar equipo", result.getTitulo());
        verify(tareaDAO, times(1)).findByIdTarea(1);
    }

    @Test
    void testFindByIdWhenTareaDoesNotExist() {
        // Arrange
        when(tareaDAO.findByIdTarea(1)).thenReturn(Optional.empty());

        // Act
        TareaDto result = tareaService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId());
        verify(tareaDAO, times(1)).findByIdTarea(1);
    }

    @Test
    void testCreateTarea() {
        // Arrange
        TareaDto tareaDto = buildTareaDto();

        // Act
        ResponseCreated response = tareaService.create(tareaDto);

        // Assert
        assertEquals("Tarea Creado Exitosamente", response.getMessage());
        verify(tareaDAO, times(1)).saveTarea(any(Tarea.class));
    }

    @Test
    void testUpdateTareaWhenTareaExists() {
        // Arrange
        TareaDto tareaDto = buildTareaDto();
        tareaDto.setTitulo("Revisar equipo actualizado");

        Tarea existingTarea = new Tarea();
        existingTarea.setId(1);

        when(tareaDAO.findByIdTarea(1)).thenReturn(Optional.of(existingTarea));

        // Act
        ResponseCreated response = tareaService.update(tareaDto, 1);

        // Assert
        assertEquals("Tarea Actualizado Exitosamente", response.getMessage());
        assertEquals("Revisar equipo actualizado", existingTarea.getTitulo());
        assertNotNull(existingTarea.getTipoTarea());
        assertNotNull(existingTarea.getEstado());
        assertNotNull(existingTarea.getPrioridad());
        verify(tareaDAO, times(1)).saveTarea(existingTarea);
    }

    @Test
    void testUpdateTareaWhenTareaDoesNotExist() {
        // Arrange
        TareaDto tareaDto = buildTareaDto();
        when(tareaDAO.findByIdTarea(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = tareaService.update(tareaDto, 1);

        // Assert
        assertEquals("Tarea no encontrado en la base de Datos", response.getMessage());
        verify(tareaDAO, times(0)).saveTarea(any(Tarea.class));
    }

    @Test
    void testDeleteTareaWhenTareaExists() {
        // Arrange
        Tarea existingTarea = new Tarea();
        existingTarea.setId(1);
        when(tareaDAO.findByIdTarea(1)).thenReturn(Optional.of(existingTarea));

        // Act
        ResponseCreated response = tareaService.delete(1);

        // Assert
        assertEquals("Tarea con ID -> 1 <- Eliminado Exitosamente", response.getMessage());
        verify(tareaDAO, times(1)).deleteTarea(1);
    }

    @Test
    void testDeleteTareaWhenTareaDoesNotExist() {
        // Arrange
        when(tareaDAO.findByIdTarea(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = tareaService.delete(1);

        // Assert
        assertEquals("Tarea con ID -> 1 <- No Encontrado", response.getMessage());
        verify(tareaDAO, times(0)).deleteTarea(anyInt());
    }

    @Test
    void testFindByUserIdWhenTareaExists() {
        // Arrange
        Tarea tarea = new Tarea();
        tarea.setId(5);
        when(tareaDAO.findByUsuarioId(9)).thenReturn(Optional.of(tarea));

        // Act
        TareaDto result = tareaService.findByUserId(9);

        // Assert
        assertNotNull(result);
        assertEquals(5, result.getId());
        verify(tareaDAO, times(1)).findByUsuarioId(9);
    }

    @Test
    void testFindByUserIdWhenTareaDoesNotExist() {
        // Arrange
        when(tareaDAO.findByUsuarioId(9)).thenReturn(Optional.empty());

        // Act
        TareaDto result = tareaService.findByUserId(9);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId());
        verify(tareaDAO, times(1)).findByUsuarioId(9);
    }
}
