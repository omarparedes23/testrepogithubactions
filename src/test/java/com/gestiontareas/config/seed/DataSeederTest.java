package com.gestiontareas.config.seed;

import com.gestiontareas.model.entities.Cargo;
import com.gestiontareas.model.entities.Usuario;
import com.gestiontareas.model.repository.CargoRepository;
import com.gestiontareas.model.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DataSeederTest {

    @Mock
    private CargoRepository cargoRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private DataSeeder dataSeeder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Simular codificación de contraseñas
        when(passwordEncoder.encode(anyString())).thenAnswer(invocation -> "encoded_" + invocation.getArgument(0));

        // Simular que la base de datos está vacía para que execute la lógica
        when(usuarioRepository.count()).thenReturn(0L);

        dataSeeder = new DataSeeder(cargoRepository, usuarioRepository, passwordEncoder);
    }

    @Test
    void testRunSeedsCargosAndUsuarios() {
        // Act
        dataSeeder.run();

        // Assert
        ArgumentCaptor<List<Cargo>> cargoCaptor = ArgumentCaptor.forClass(List.class);
        verify(cargoRepository, times(1)).saveAll(cargoCaptor.capture());
        assertEquals(5, cargoCaptor.getValue().size());

        ArgumentCaptor<List<Usuario>> usuarioCaptor = ArgumentCaptor.forClass(List.class);
        verify(usuarioRepository, times(1)).saveAll(usuarioCaptor.capture());
        List<Usuario> usuarios = usuarioCaptor.getValue();
        assertEquals(5, usuarios.size());

        // Verificar que las contraseñas pasaron por el encoder mockeado
        assertTrue(usuarios.stream().allMatch(u -> u.getPassword().startsWith("encoded_")));
    }

    @Test
    void testRunDoesNotSeedWhenDataAlreadyExists() {
        // Simular que ya hay datos guardados
        when(usuarioRepository.count()).thenReturn(5L);

        dataSeeder.run();

        // Verificar que NUNCA intentó guardar nada
        verify(cargoRepository, never()).saveAll(any());
        verify(usuarioRepository, never()).saveAll(any());
    }
}