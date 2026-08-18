package com.gestiontareas.config.auth.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gestiontareas.config.auth.endpublic.AuthResponse;
import com.gestiontareas.config.auth.endpublic.LoginRequest;
import com.gestiontareas.config.auth.endpublic.ValidateRequest;
import com.gestiontareas.config.auth.endpublic.ValidateResponse;
import com.gestiontareas.config.util.JwtUtils;
import com.gestiontareas.model.entities.Cargo;
import com.gestiontareas.model.entities.CargoEnum;
import com.gestiontareas.model.entities.Usuario;
import com.gestiontareas.model.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private AuthService authService;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        Cargo cargo = new Cargo();
        cargo.setId(1);
        cargo.setCargoNombre(CargoEnum.ADMIN);

        usuario = Usuario.builder()
                .id(1)
                .nombre("Juan")
                .apellido("Perez")
                .telefono("999999999")
                .email("juan@test.com")
                .isEnabled(true)
                .fechaCreacion(LocalDate.now())
                .horarioTrabajo("08:00-17:00")
                .imagen("img.png")
                .username("jperez")
                .password("encoded-password")
                .cargo(cargo)
                .build();
    }

    @Test
    void testLoadUserByUsernameWhenUserExistsReturnsUserDetails() {
        when(usuarioRepository.findByUsername("jperez")).thenReturn(Optional.of(usuario));

        UserDetails userDetails = authService.loadUserByUsername("jperez");

        assertNotNull(userDetails);
        assertEquals("jperez", userDetails.getUsername());
        assertEquals("encoded-password", userDetails.getPassword());
        assertTrue(userDetails.isEnabled());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ADMIN")));
    }

    @Test
    void testLoadUserByUsernameWhenUserDoesNotExistThrowsException() {
        when(usuarioRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> authService.loadUserByUsername("unknown"));
    }

    @Test
    void testValidateWithValidTokenReturnsTrue() {
        DecodedJWT decodedJWT = mock(DecodedJWT.class);
        when(jwtUtils.validateToken("valid-token")).thenReturn(decodedJWT);

        ValidateResponse response = authService.validate(new ValidateRequest("valid-token"));

        assertTrue(response.isValidate());
    }

    @Test
    void testValidateWithInvalidTokenReturnsFalse() {
        when(jwtUtils.validateToken("invalid-token"))
                .thenThrow(new JWTVerificationException("bad token"));

        ValidateResponse response = authService.validate(new ValidateRequest("invalid-token"));

        assertFalse(response.isValidate());
    }

    @Test
    void testAuthenticationWithValidCredentialsReturnsAuthentication() {
        when(usuarioRepository.findByUsername("jperez")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("plain-password", "encoded-password")).thenReturn(true);

        Authentication authentication = authService.authentication("jperez", "plain-password");

        assertNotNull(authentication);
        assertEquals("jperez", authentication.getPrincipal());
    }

    @Test
    void testAuthenticationWithWrongPasswordThrowsBadCredentials() {
        when(usuarioRepository.findByUsername("jperez")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("wrong-password", "encoded-password")).thenReturn(false);

        assertThrows(BadCredentialsException.class,
                () -> authService.authentication("jperez", "wrong-password"));
    }

    @Test
    void testAuthenticationWithUnknownUserThrowsUsernameNotFound() {
        when(usuarioRepository.findByUsername("unknown")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class,
                () -> authService.authentication("unknown", "any-password"));
    }

    @Test
    void testLoginWithValidCredentialsReturnsAuthResponse() {
        when(usuarioRepository.findByUsername("jperez")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches("plain-password", "encoded-password")).thenReturn(true);
        when(jwtUtils.create(any(Authentication.class))).thenReturn("generated-jwt-token");

        AuthResponse response = authService.login(new LoginRequest("jperez", "plain-password"));

        assertNotNull(response);
        assertEquals("generated-jwt-token", response.token());
        assertEquals("jperez", response.username());
        assertEquals("Juan", response.name());
        assertEquals("ADMIN", response.role());
        assertTrue(response.status());
        assertEquals("Usuario autenticado", response.message());
        verify(jwtUtils, times(1)).create(any(Authentication.class));
    }

    @Test
    void testLoginWithInvalidCredentialsThrowsBadCredentials() {
        when(usuarioRepository.findByUsername("jperez")).thenReturn(Optional.of(usuario));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        assertThrows(BadCredentialsException.class,
                () -> authService.login(new LoginRequest("jperez", "wrong-password")));

        verify(jwtUtils, never()).create(any(Authentication.class));
    }
}
