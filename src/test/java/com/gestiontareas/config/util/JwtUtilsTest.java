package com.gestiontareas.config.util;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    private JwtUtils jwtUtils;

    private static final String PRIVATE_KEY = "test-secret-key-for-jwt-signing";
    private static final String USER_GENERATOR = "gestiontareas-app";

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(jwtUtils, "privateKey", PRIVATE_KEY);
        ReflectionTestUtils.setField(jwtUtils, "userGenerator", USER_GENERATOR);
    }

    @Test
    void testCreateTokenGeneratesNonEmptyToken() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "jdoe", "password", List.of(new SimpleGrantedAuthority("ADMIN")));

        String token = jwtUtils.create(authentication);

        assertNotNull(token);
        assertFalse(token.isEmpty());
        assertEquals(3, token.split("\\.").length); // header.payload.signature
    }

    @Test
    void testCreateTokenIncludesAuthoritiesClaim() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "jdoe", "password", List.of(new SimpleGrantedAuthority("ADMIN"), new SimpleGrantedAuthority("SOPORTE")));

        String token = jwtUtils.create(authentication);
        DecodedJWT decodedJWT = jwtUtils.validateToken(token);

        assertEquals("ADMIN,SOPORTE", decodedJWT.getClaim("authorities").asString());
    }

    @Test
    void testValidateTokenWithValidTokenReturnsDecodedJWT() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "jdoe", "password", List.of(new SimpleGrantedAuthority("ADMIN")));
        String token = jwtUtils.create(authentication);

        DecodedJWT decodedJWT = jwtUtils.validateToken(token);

        assertNotNull(decodedJWT);
        assertEquals("jdoe", decodedJWT.getSubject());
        assertEquals(USER_GENERATOR, decodedJWT.getIssuer());
    }

    @Test
    void testValidateTokenWithMalformedTokenThrowsException() {
        assertThrows(JWTVerificationException.class,
                () -> jwtUtils.validateToken("not-a-valid-jwt-token"));
    }

    @Test
    void testValidateTokenWithWrongSignatureThrowsException() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "jdoe", "password", List.of(new SimpleGrantedAuthority("ADMIN")));
        String token = jwtUtils.create(authentication);

        JwtUtils otherJwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(otherJwtUtils, "privateKey", "a-completely-different-secret");
        ReflectionTestUtils.setField(otherJwtUtils, "userGenerator", USER_GENERATOR);

        assertThrows(JWTVerificationException.class,
                () -> otherJwtUtils.validateToken(token));
    }

    @Test
    void testValidateTokenWithWrongIssuerThrowsException() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "jdoe", "password", List.of(new SimpleGrantedAuthority("ADMIN")));
        String token = jwtUtils.create(authentication);

        JwtUtils otherJwtUtils = new JwtUtils();
        ReflectionTestUtils.setField(otherJwtUtils, "privateKey", PRIVATE_KEY);
        ReflectionTestUtils.setField(otherJwtUtils, "userGenerator", "other-issuer");

        assertThrows(JWTVerificationException.class,
                () -> otherJwtUtils.validateToken(token));
    }

    @Test
    void testExtractUsernameReturnsSubject() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "msmith", "password", List.of(new SimpleGrantedAuthority("USER")));
        String token = jwtUtils.create(authentication);
        DecodedJWT decodedJWT = jwtUtils.validateToken(token);

        assertEquals("msmith", jwtUtils.extractUsername(decodedJWT));
    }

    @Test
    void testGetSpecificClaimReturnsRequestedClaim() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                "jdoe", "password", List.of(new SimpleGrantedAuthority("ADMIN")));
        String token = jwtUtils.create(authentication);
        DecodedJWT decodedJWT = jwtUtils.validateToken(token);

        Claim claim = jwtUtils.getSpecificClaim(decodedJWT, "authorities");

        assertNotNull(claim);
        assertEquals("ADMIN", claim.asString());
    }
}
