package com.gestiontareas.service.implemetacion;

import com.gestiontareas.model.dao.IUsuarioDAO;
import com.gestiontareas.model.entities.Cargo;
import com.gestiontareas.model.entities.Usuario;
import com.gestiontareas.presentacion.dto.UsuarioDto;
import com.gestiontareas.service.interfaces.IStorageService;
import com.gestiontareas.service.interfaces.IUsuarioService;
import com.gestiontareas.utils.response.ResponseCreated;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioServiceImpl implements IUsuarioService {

    @Autowired
    private IUsuarioDAO usuarioDAO;

    @Autowired
    private IStorageService storageService;


    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public List<UsuarioDto> findAll() {

        ModelMapper modelMapper = new ModelMapper();

        return this.usuarioDAO.findAllUser()
                .stream()
                .map(entity -> modelMapper.map(entity,UsuarioDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto findById(Integer id) {
        Optional<Usuario> usuario = this.usuarioDAO.findByIdUser(id);

        if(usuario.isPresent()){
            ModelMapper modelMapper = new ModelMapper();
            Usuario currentUsuario = usuario.get();
            return modelMapper.map(currentUsuario, UsuarioDto.class);
        }else{
            return new UsuarioDto();
        }
    }

    @Override
    public ResponseCreated createUser(UsuarioDto usuarioDto) {

        try{

            ModelMapper modelMapper = new ModelMapper();

            Cargo cargo = modelMapper.map(usuarioDto.getCargo(),Cargo.class);

            usuarioDto.setFecha(LocalDate.now());

            Usuario usuario = new Usuario();
            usuario.setNombre(usuarioDto.getNombre());
            usuario.setApellido(usuarioDto.getApellido());
            usuario.setTelefono(usuarioDto.getTelefono());
            usuario.setImagen(usuarioDto.getImagen());
            usuario.setFechaCreacion(usuarioDto.getFecha());
            usuario.setHorarioTrabajo(usuarioDto.getHorario());
            usuario.setEmail(usuarioDto.getEmail());
            usuario.setEnabled(usuarioDto.isEnabled());
            usuario.setUsername(usuarioDto.getUsername());
            usuario.setPassword(bCryptPasswordEncoder.encode(usuarioDto.getPassword()));
            usuario.setCargo(cargo);
            this.usuarioDAO.saveUser(usuario);

            return ResponseCreated.builder()
                    .message("Usuario Creado Exitosamente")
                    .build();
        }catch(Exception ex){
            throw new UnsupportedOperationException("Error al guardar el usuario "+ ex.getMessage() );
        }
    }

    @Override
    public ResponseCreated updateUser(UsuarioDto usuarioDto, Integer id) {
        Optional<Usuario> usuario = this.usuarioDAO.findByIdUser(id);

        if(usuario.isPresent()){
            Usuario usuarioEntity = usuario.get();
            String imagenAnterior = usuarioEntity.getImagen();

            usuarioEntity.setNombre(usuarioDto.getNombre());
            usuarioEntity.setApellido(usuarioDto.getApellido());
            usuarioEntity.setTelefono(usuarioDto.getTelefono());
            usuarioEntity.setImagen(usuarioDto.getImagen());
            usuarioEntity.setFechaCreacion(usuarioDto.getFecha());
            usuarioEntity.setHorarioTrabajo(usuarioDto.getHorario());
            usuarioEntity.setEnabled(usuarioDto.isEnabled());
            usuarioEntity.setUsername(usuarioDto.getUsername());
            usuarioEntity.setEmail(usuarioDto.getEmail());

            if(imagenAnterior != null && !imagenAnterior.equals(usuarioDto.getImagen())){
                storageService.delete(imagenAnterior);
            }

            // Password write-only: el cliente ya no recibe el hash de vuelta.
            // Si viene vacio/null, se interpreta como "no cambiar la contrasena".
            // Si trae valor, es una contrasena en texto plano nueva y se encripta.
            if(usuarioDto.getPassword() != null && !usuarioDto.getPassword().isBlank()){
                usuarioEntity.setPassword(bCryptPasswordEncoder.encode(usuarioDto.getPassword()));
            }

            ModelMapper modelMapper = new ModelMapper();
            Cargo cargo = modelMapper.map(usuarioDto.getCargo(),Cargo.class);
            usuarioEntity.setCargo(cargo);

            this.usuarioDAO.saveUser(usuarioEntity);

            return ResponseCreated.builder()
                    .message("Usuario Actualizado Exitosamente")
                    .build();
        }
        else{
            return ResponseCreated.builder()
                    .message("Usuario no encontrado en la base de Datos")
                    .build();
        }
    }

    @Override
    public ResponseCreated deleteUser(Integer id) {
        Optional<Usuario> usuario = this.usuarioDAO.findByIdUser(id);

        if(usuario.isPresent()){
            Usuario usuarioget = usuario.get();

            storageService.delete(usuarioget.getImagen());
            this.usuarioDAO.deleteUser(id);
            return ResponseCreated.builder()
                    .message("Usuario con ID -> "+ id +" <- Eliminado Exitosamente")
                    .build();
        }else{
            return ResponseCreated.builder()
                    .message("Usuario con ID -> "+ id +" <- No Encontrado")
                    .build();
        }
    }

}
