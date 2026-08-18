package com.gestiontareas.config.auth.endpublic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gestiontareas.config.auth.services.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthControllerTest {

    @Mock
    private AuthService authService;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        AuthController controller = new AuthController();
        // AuthController uses field injection (@Autowired) so we set it via reflection helper below.
        org.springframework.test.util.ReflectionTestUtils.setField(controller, "authService", authService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void testLoginReturnsAuthResponse() throws Exception {
        LoginRequest request = new LoginRequest("jperez", "plain-password");
        AuthResponse expected = new AuthResponse(
                "generated-jwt-token", "ADMIN", "jperez", "Juan", "Usuario autenticado", true);

        when(authService.login(any(LoginRequest.class))).thenReturn(expected);

        mockMvc.perform(post("/auth/login")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value("generated-jwt-token"))
                .andExpect(jsonPath("$.username").value("jperez"))
                .andExpect(jsonPath("$.role").value("ADMIN"))
                .andExpect(jsonPath("$.status").value(true));

        verify(authService, times(1)).login(any(LoginRequest.class));
    }

    @Test
    void testValidateReturnsValidateResponseTrue() throws Exception {
        ValidateRequest request = new ValidateRequest("some-jwt-token");
        when(authService.validate(any(ValidateRequest.class))).thenReturn(new ValidateResponse(true));

        mockMvc.perform(post("/auth/validate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValidate").value(true));

        verify(authService, times(1)).validate(any(ValidateRequest.class));
    }

    @Test
    void testValidateReturnsValidateResponseFalseForInvalidToken() throws Exception {
        ValidateRequest request = new ValidateRequest("bad-token");
        when(authService.validate(any(ValidateRequest.class))).thenReturn(new ValidateResponse(false));

        mockMvc.perform(post("/auth/validate")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isValidate").value(false));
    }
}
