package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.ITipoTareaDAO;
import com.gestiontareas.model.entities.TipoTarea;
import com.gestiontareas.presentacion.dto.TipoTareaDto;
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

public class TipoTareaServiceImplTest {

    @Mock
    private ITipoTareaDAO tipoTareaDAO;

    @InjectMocks
    private TipoTareaServiceImpl tipoTareaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        TipoTarea tipoTarea1 = new TipoTarea();
        TipoTarea tipoTarea2 = new TipoTarea();
        when(tipoTareaDAO.findAllTipoTarea()).thenReturn(Arrays.asList(tipoTarea1, tipoTarea2));

        // Act
        List<TipoTareaDto> result = tipoTareaService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(tipoTareaDAO, times(1)).findAllTipoTarea();
    }

    @Test
    void testFindByIdWhenTipoTareaExists() {
        // Arrange
        TipoTarea tipoTarea = new TipoTarea();
        tipoTarea.setId(1);
        tipoTarea.setTipoTareaNombre("Correctivo");
        when(tipoTareaDAO.findByIdTipoTarea(1)).thenReturn(Optional.of(tipoTarea));

        // Act
        TipoTareaDto result = tipoTareaService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Correctivo", result.getTipo());
        verify(tipoTareaDAO, times(1)).findByIdTipoTarea(1);
    }

    @Test
    void testFindByIdWhenTipoTareaDoesNotExist() {
        // Arrange
        when(tipoTareaDAO.findByIdTipoTarea(1)).thenReturn(Optional.empty());

        // Act
        TipoTareaDto result = tipoTareaService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId());
        verify(tipoTareaDAO, times(1)).findByIdTipoTarea(1);
    }

    @Test
    void testCreateTipoTarea() {
        // Arrange
        TipoTareaDto tipoTareaDto = new TipoTareaDto();
        tipoTareaDto.setTipo("Preventivo");
        tipoTareaDto.setImagen("preventivo.png");

        // Act
        ResponseCreated response = tipoTareaService.create(tipoTareaDto);

        // Assert
        assertEquals("TipoTarea Creado Exitosamente", response.getMessage());
        verify(tipoTareaDAO, times(1)).saveTipoTarea(any(TipoTarea.class));
    }

    @Test
    void testUpdateTipoTareaWhenTipoTareaExists() {
        // Arrange
        TipoTareaDto tipoTareaDto = new TipoTareaDto();
        tipoTareaDto.setTipo("Correctivo");
        tipoTareaDto.setImagen("correctivo.png");
        TipoTarea existingTipoTarea = new TipoTarea();
        existingTipoTarea.setId(1);

        when(tipoTareaDAO.findByIdTipoTarea(1)).thenReturn(Optional.of(existingTipoTarea));

        // Act
        ResponseCreated response = tipoTareaService.update(tipoTareaDto, 1);

        // Assert
        assertEquals("TipoTarea Actualizado Exitosamente", response.getMessage());
        assertEquals("Correctivo", existingTipoTarea.getTipoTareaNombre());
        assertEquals("correctivo.png", existingTipoTarea.getImagen());
        verify(tipoTareaDAO, times(1)).saveTipoTarea(existingTipoTarea);
    }

    @Test
    void testUpdateTipoTareaWhenTipoTareaDoesNotExist() {
        // Arrange
        TipoTareaDto tipoTareaDto = new TipoTareaDto();
        when(tipoTareaDAO.findByIdTipoTarea(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = tipoTareaService.update(tipoTareaDto, 1);

        // Assert
        assertEquals("TipoTarea no encontrado en la base de Datos", response.getMessage());
        verify(tipoTareaDAO, times(0)).saveTipoTarea(any(TipoTarea.class));
    }

    @Test
    void testDeleteTipoTareaWhenTipoTareaExists() {
        // Arrange
        TipoTarea existingTipoTarea = new TipoTarea();
        existingTipoTarea.setId(1);
        when(tipoTareaDAO.findByIdTipoTarea(1)).thenReturn(Optional.of(existingTipoTarea));

        // Act
        ResponseCreated response = tipoTareaService.delete(1);

        // Assert
        assertEquals("TipoTarea con ID -> 1 <- Eliminado Exitosamente", response.getMessage());
        verify(tipoTareaDAO, times(1)).deleteTipoTarea(1);
    }

    @Test
    void testDeleteTipoTareaWhenTipoTareaDoesNotExist() {
        // Arrange
        when(tipoTareaDAO.findByIdTipoTarea(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = tipoTareaService.delete(1);

        // Assert
        assertEquals("TipoTarea con ID -> 1 <- No Encontrado", response.getMessage());
        verify(tipoTareaDAO, times(0)).deleteTipoTarea(anyInt());
    }
}
