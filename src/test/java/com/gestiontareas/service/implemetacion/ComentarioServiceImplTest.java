package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.IComentarioDAO;
import com.gestiontareas.model.entities.Comentario;
import com.gestiontareas.presentacion.dto.ComentarioDto;
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

public class ComentarioServiceImplTest {

    @Mock
    private IComentarioDAO comentarioDAO;

    @InjectMocks
    private ComentarioServiceImpl comentarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        // Arrange
        Comentario comentario1 = new Comentario();
        Comentario comentario2 = new Comentario();
        when(comentarioDAO.findAllComentario()).thenReturn(Arrays.asList(comentario1, comentario2));

        // Act
        List<ComentarioDto> result = comentarioService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(comentarioDAO, times(1)).findAllComentario();
    }

    @Test
    void testFindByIdWhenComentarioExists() {
        // Arrange
        Comentario comentario = new Comentario();
        comentario.setId(1);
        comentario.setComentario("Un comentario");
        when(comentarioDAO.findByIdComentario(1)).thenReturn(Optional.of(comentario));

        // Act
        ComentarioDto result = comentarioService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Un comentario", result.getComentario());
        verify(comentarioDAO, times(1)).findByIdComentario(1);
    }

    @Test
    void testFindByIdWhenComentarioDoesNotExist() {
        // Arrange
        when(comentarioDAO.findByIdComentario(1)).thenReturn(Optional.empty());

        // Act
        ComentarioDto result = comentarioService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId());
        verify(comentarioDAO, times(1)).findByIdComentario(1);
    }

    @Test
    void testCreateComentario() {
        // Arrange
        ComentarioDto comentarioDto = new ComentarioDto();
        comentarioDto.setComentario("Nuevo comentario");

        // Act
        ResponseCreated response = comentarioService.create(comentarioDto);

        // Assert
        assertEquals("Comentario Creado Exitosamente", response.getMessage());
        verify(comentarioDAO, times(1)).saveComentario(any(Comentario.class));
    }

    @Test
    void testUpdateComentarioWhenComentarioExists() {
        // Arrange
        ComentarioDto comentarioDto = new ComentarioDto();
        comentarioDto.setComentario("Comentario actualizado");
        Comentario existingComentario = new Comentario();
        existingComentario.setId(1);

        when(comentarioDAO.findByIdComentario(1)).thenReturn(Optional.of(existingComentario));

        // Act
        ResponseCreated response = comentarioService.update(comentarioDto, 1);

        // Assert
        assertEquals("Comentario Actualizado Exitosamente", response.getMessage());
        assertEquals("Comentario actualizado", existingComentario.getComentario());
        verify(comentarioDAO, times(1)).saveComentario(existingComentario);
    }

    @Test
    void testUpdateComentarioWhenComentarioDoesNotExist() {
        // Arrange
        ComentarioDto comentarioDto = new ComentarioDto();
        when(comentarioDAO.findByIdComentario(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = comentarioService.update(comentarioDto, 1);

        // Assert
        assertEquals("Comentario no encontrado en la base de Datos", response.getMessage());
        verify(comentarioDAO, times(0)).saveComentario(any(Comentario.class));
    }

    @Test
    void testDeleteComentarioWhenComentarioExists() {
        // Arrange
        Comentario existingComentario = new Comentario();
        existingComentario.setId(1);
        when(comentarioDAO.findByIdComentario(1)).thenReturn(Optional.of(existingComentario));

        // Act
        ResponseCreated response = comentarioService.delete(1);

        // Assert
        assertEquals("Comentario con ID -> 1 <- Eliminado Exitosamente", response.getMessage());
        verify(comentarioDAO, times(1)).deleteComentario(1);
    }

    @Test
    void testDeleteComentarioWhenComentarioDoesNotExist() {
        // Arrange
        when(comentarioDAO.findByIdComentario(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = comentarioService.delete(1);

        // Assert
        assertEquals("Comentario con ID -> 1 <- No Encontrado", response.getMessage());
        verify(comentarioDAO, times(0)).deleteComentario(anyInt());
    }
}
