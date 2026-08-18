package com.gestiontareas.config.auth.services;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.gestiontareas.config.auth.endpublic.AuthResponse;
import com.gestiontareas.config.auth.endpublic.LoginRequest;
import com.gestiontareas.config.auth.endpublic.ValidateRequest;
import com.gestiontareas.config.auth.endpublic.ValidateResponse;
import com.gestiontareas.config.util.JwtUtils;
import com.gestiontareas.model.dao.IUsuarioDAO;
import com.gestiontareas.model.repository.UsuarioRepository;
import com.gestiontareas.model.entities.Usuario;
import com.gestiontareas.presentacion.dto.UsuarioDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class AuthService implements UserDetailsService {

    @Autowired
    private JwtUtils jwtUtils;


    @Autowired
    private  PasswordEncoder passwordEncoder;

    @Autowired
    private UsuarioRepository usuarioRepository;


    @Override
    public UserDetails loadUserByUsername(String username){

        Usuario userEntities = usuarioRepository.findByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User not found with username: " + username)
                );
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        authorities.add(new SimpleGrantedAuthority(userEntities.getCargo().getCargoNombre().name()));



        return new User(
                userEntities.getUsername(),
                userEntities.getPassword(),
                userEntities.isEnabled(),
                true,
                true,
                true,
                authorities
        );

    }

    public ValidateResponse validate(ValidateRequest token){
      try{
          DecodedJWT decodedJWT = jwtUtils.validateToken(token.token());


          return new ValidateResponse(true);
      }catch (JWTVerificationException e){
          return new ValidateResponse(false);
      }


    }

    public AuthResponse login(LoginRequest authLoginrequest) {

        String username = authLoginrequest.username();
        String password = authLoginrequest.password();

        Authentication authentication = this.authentication(username, password);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accestoken = jwtUtils.create(authentication);

        // Desempaquetamos directamente o lanzamos excepción si no existe
        Usuario currentUsuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con username: " + username));

        ModelMapper modelMapper = new ModelMapper();
        UsuarioDto user = modelMapper.map(currentUsuario, UsuarioDto.class);

        return new AuthResponse(
                accestoken,
                user.getCargo().getCargo().name(),
                username,
                user.getNombre(),
                "Usuario autenticado",
                true
        );
    }

    public Authentication authentication(String username, String password) {
        UserDetails userDetails = this.loadUserByUsername(username);

        if(userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        if(!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }

        return new UsernamePasswordAuthenticationToken(username, password, userDetails.getAuthorities());
    }


}
