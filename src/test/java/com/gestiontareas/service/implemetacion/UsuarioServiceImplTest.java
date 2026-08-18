package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.IUsuarioDAO;
import com.gestiontareas.model.entities.CargoEnum;
import com.gestiontareas.model.entities.Usuario;
import com.gestiontareas.presentacion.dto.CargoDto;
import com.gestiontareas.presentacion.dto.UsuarioDto;
import com.gestiontareas.service.interfaces.IStorageService;
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

public class UsuarioServiceImplTest {

    @Mock
    private IUsuarioDAO usuarioDAO;

    @Mock
    private IStorageService storageService;

    @InjectMocks
    private UsuarioServiceImpl usuarioService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private UsuarioDto buildUsuarioDto() {
        UsuarioDto dto = new UsuarioDto();
        dto.setNombre("Juan");
        dto.setApellido("Perez");
        dto.setTelefono("999999999");
        dto.setImagen("foto.jpg");
        dto.setHorario("TARDE");
        dto.setEmail("juan.perez@example.com");
        dto.setEnabled(true);
        dto.setUsername("jperez");
        dto.setPassword("clave123");

        CargoDto cargoDto = new CargoDto();
        cargoDto.setCargo(CargoEnum.USER);
        dto.setCargo(cargoDto);
        return dto;
    }

    @Test
    void testFindAll() {
        // Arrange
        Usuario usuario1 = new Usuario();
        Usuario usuario2 = new Usuario();
        when(usuarioDAO.findAllUser()).thenReturn(Arrays.asList(usuario1, usuario2));

        // Act
        List<UsuarioDto> result = usuarioService.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(usuarioDAO, times(1)).findAllUser();
    }

    @Test
    void testFindByIdWhenUsuarioExists() {
        // Arrange
        Usuario usuario = new Usuario();
        usuario.setId(1);
        usuario.setNombre("Juan");
        when(usuarioDAO.findByIdUser(1)).thenReturn(Optional.of(usuario));

        // Act
        UsuarioDto result = usuarioService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals("Juan", result.getNombre());
        verify(usuarioDAO, times(1)).findByIdUser(1);
    }

    @Test
    void testFindByIdWhenUsuarioDoesNotExist() {
        // Arrange
        when(usuarioDAO.findByIdUser(1)).thenReturn(Optional.empty());

        // Act
        UsuarioDto result = usuarioService.findById(1);

        // Assert
        assertNotNull(result);
        assertEquals(0, result.getId());
        verify(usuarioDAO, times(1)).findByIdUser(1);
    }

    @Test
    void testCreateUser() {
        // Arrange
        UsuarioDto usuarioDto = buildUsuarioDto();

        // Act
        ResponseCreated response = usuarioService.createUser(usuarioDto);

        // Assert
        assertEquals("Usuario Creado Exitosamente", response.getMessage());
        verify(usuarioDAO, times(1)).saveUser(any(Usuario.class));
    }

    @Test
    void testUpdateUserWhenUsuarioExists() {
        // Arrange
        UsuarioDto usuarioDto = buildUsuarioDto();
        usuarioDto.setImagen("foto.jpg");

        Usuario existingUsuario = new Usuario();
        existingUsuario.setId(1);
        existingUsuario.setImagen("foto.jpg");
        existingUsuario.setPassword("hash-existente");

        when(usuarioDAO.findByIdUser(1)).thenReturn(Optional.of(existingUsuario));

        // Act
        ResponseCreated response = usuarioService.updateUser(usuarioDto, 1);

        // Assert
        assertEquals("Usuario Actualizado Exitosamente", response.getMessage());
        assertEquals("Juan", existingUsuario.getNombre());
        // Se envio una contrasena nueva en texto plano: debe quedar encriptada
        assertNotEquals("clave123", existingUsuario.getPassword());
        assertNotEquals("hash-existente", existingUsuario.getPassword());
        // La imagen no cambio, por lo que no se debe eliminar el archivo anterior
        verify(storageService, times(0)).delete(anyString());
        verify(usuarioDAO, times(1)).saveUser(existingUsuario);
    }

    @Test
    void testUpdateUserKeepsPasswordWhenNotProvided() {
        // Arrange: el DTO llega sin password (write-only, el cliente nunca la reenvia)
        UsuarioDto usuarioDto = buildUsuarioDto();
        usuarioDto.setPassword(null);
        usuarioDto.setImagen("foto.jpg");

        Usuario existingUsuario = new Usuario();
        existingUsuario.setId(1);
        existingUsuario.setImagen("foto.jpg");
        existingUsuario.setPassword("hash-existente");

        when(usuarioDAO.findByIdUser(1)).thenReturn(Optional.of(existingUsuario));

        // Act
        usuarioService.updateUser(usuarioDto, 1);

        // Assert: la contrasena no debe tocarse
        assertEquals("hash-existente", existingUsuario.getPassword());
        verify(usuarioDAO, times(1)).saveUser(existingUsuario);
    }

    @Test
    void testUpdateUserEncodesNewPasswordWhenProvided() {
        // Arrange
        UsuarioDto usuarioDto = buildUsuarioDto();
        usuarioDto.setPassword("nuevaClaveSegura");
        usuarioDto.setImagen("foto.jpg");

        Usuario existingUsuario = new Usuario();
        existingUsuario.setId(1);
        existingUsuario.setImagen("foto.jpg");
        existingUsuario.setPassword("hash-existente");

        when(usuarioDAO.findByIdUser(1)).thenReturn(Optional.of(existingUsuario));

        // Act
        usuarioService.updateUser(usuarioDto, 1);

        // Assert: la nueva contrasena debe quedar encriptada, nunca en texto plano
        assertNotEquals("nuevaClaveSegura", existingUsuario.getPassword());
        assertNotEquals("hash-existente", existingUsuario.getPassword());
        verify(usuarioDAO, times(1)).saveUser(existingUsuario);
    }

    @Test
    void testUpdateUserReplacesImageWhenChanged() {
        // Arrange
        UsuarioDto usuarioDto = buildUsuarioDto();
        usuarioDto.setImagen("foto-nueva.jpg");

        Usuario existingUsuario = new Usuario();
        existingUsuario.setId(1);
        existingUsuario.setImagen("foto-vieja.jpg");
        existingUsuario.setPassword("hash-existente");

        when(usuarioDAO.findByIdUser(1)).thenReturn(Optional.of(existingUsuario));

        // Act
        usuarioService.updateUser(usuarioDto, 1);

        // Assert: al cambiar la imagen, se elimina la anterior del storage
        verify(storageService, times(1)).delete("foto-vieja.jpg");
        verify(usuarioDAO, times(1)).saveUser(existingUsuario);
    }

    @Test
    void testUpdateUserWhenUsuarioDoesNotExist() {
        // Arrange
        UsuarioDto usuarioDto = buildUsuarioDto();
        when(usuarioDAO.findByIdUser(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = usuarioService.updateUser(usuarioDto, 1);

        // Assert
        assertEquals("Usuario no encontrado en la base de Datos", response.getMessage());
        verify(usuarioDAO, times(0)).saveUser(any(Usuario.class));
    }

    @Test
    void testDeleteUserWhenUsuarioExists() {
        // Arrange
        Usuario existingUsuario = new Usuario();
        existingUsuario.setId(1);
        existingUsuario.setImagen("foto.jpg");
        when(usuarioDAO.findByIdUser(1)).thenReturn(Optional.of(existingUsuario));

        // Act
        ResponseCreated response = usuarioService.deleteUser(1);

        // Assert
        assertEquals("Usuario con ID -> 1 <- Eliminado Exitosamente", response.getMessage());
        verify(storageService, times(1)).delete("foto.jpg");
        verify(usuarioDAO, times(1)).deleteUser(1);
    }

    @Test
    void testDeleteUserWhenUsuarioDoesNotExist() {
        // Arrange
        when(usuarioDAO.findByIdUser(1)).thenReturn(Optional.empty());

        // Act
        ResponseCreated response = usuarioService.deleteUser(1);

        // Assert
        assertEquals("Usuario con ID -> 1 <- No Encontrado", response.getMessage());
        verify(storageService, times(0)).delete(anyString());
        verify(usuarioDAO, times(0)).deleteUser(anyInt());
    }
}
