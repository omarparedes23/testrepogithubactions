package com.gestiontareas.config.seed;

import com.gestiontareas.model.entities.Cargo;
import com.gestiontareas.model.entities.CargoEnum;
import com.gestiontareas.model.entities.Usuario;
import com.gestiontareas.model.repository.CargoRepository;
import com.gestiontareas.model.repository.UsuarioRepository;
import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Carga datos de ejemplo (cargos y usuarios) al iniciar la aplicación en entornos de desarrollo/pruebas.
 */
@Component
@ConditionalOnProperty(name = "app.data-seed.enabled", havingValue = "true")
public class DataSeeder implements CommandLineRunner {

    private final CargoRepository cargoRepository;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // Se asignan valores por defecto en la declaración para evitar que sean null en tests unitarios simples
    @Value("${app.seed.passwords.admin:admin123}")
    private String passAdmin = "admin123";

    @Value("${app.seed.passwords.mantenimiento:mantenimiento123}")
    private String passMantenimiento = "mantenimiento123";

    @Value("${app.seed.passwords.soporte:soporte123}")
    private String passSoporte = "soporte123";

    @Value("${app.seed.passwords.limpieza:limpieza123}")
    private String passLimpieza = "limpieza123";

    @Value("${app.seed.passwords.user:user123}")
    private String passUser = "user123";

    public DataSeeder(CargoRepository cargoRepository,
                      UsuarioRepository usuarioRepository,
                      PasswordEncoder passwordEncoder) {
        this.cargoRepository = cargoRepository;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Evitamos duplicar datos si la base de datos ya contiene registros
        if (usuarioRepository.count() > 0) {
            return;
        }

        List<Cargo> cargos = crearCargos();
        cargoRepository.saveAll(cargos);

        List<Usuario> usuarios = crearUsuarios(cargos);
        usuarioRepository.saveAll(usuarios);
    }

    private List<Cargo> crearCargos() {
        Cargo cargoAdmin = Cargo.builder().cargoNombre(CargoEnum.ADMIN).build();
        Cargo cargoMantenimiento = Cargo.builder().cargoNombre(CargoEnum.MANTENIMIENTO).build();
        Cargo cargoSoporte = Cargo.builder().cargoNombre(CargoEnum.SOPORTE).build();
        Cargo cargoLimpieza = Cargo.builder().cargoNombre(CargoEnum.LIMPIEZA).build();
        Cargo cargoUser = Cargo.builder().cargoNombre(CargoEnum.USER).build();

        return List.of(cargoAdmin, cargoMantenimiento, cargoSoporte, cargoLimpieza, cargoUser);
    }

    private List<Usuario> crearUsuarios(List<Cargo> cargos) {
        Cargo cargoAdmin = cargos.get(0);
        Cargo cargoMantenimiento = cargos.get(1);
        Cargo cargoSoporte = cargos.get(2);
        Cargo cargoLimpieza = cargos.get(3);
        Cargo cargoUser = cargos.get(4);

        Usuario userAdmin = Usuario.builder()
                .cargo(cargoAdmin)
                .email("wHc7T@example.com")
                .isEnabled(true)
                .password(passwordEncoder.encode(passAdmin))
                .nombre("Marcial Aguirre")
                .fechaCreacion(LocalDate.now())
                .telefono("987654321")
                .imagen("cat01.jpeg")
                .username("admin")
                .horarioTrabajo("TARDE")
                .apellido("Don Orlando")
                .build();

        Usuario userMantenimiento = Usuario.builder()
                .cargo(cargoMantenimiento)
                .email("gqBm0@example.com")
                .isEnabled(true)
                .password(passwordEncoder.encode(passMantenimiento))
                .nombre("Emanuel Ramos")
                .fechaCreacion(LocalDate.now())
                .telefono("987654321")
                .imagen("logo.png")
                .apellido("Ezequiel Gutierrez")
                .username("mantenimiento")
                .horarioTrabajo("NOCHE")
                .build();

        Usuario userSoporte = Usuario.builder()
                .cargo(cargoSoporte)
                .email("bGcYJ@example.com")
                .isEnabled(true)
                .password(passwordEncoder.encode(passSoporte))
                .nombre("Marcelo")
                .fechaCreacion(LocalDate.now())
                .telefono("987654321")
                .imagen("cat_war01.jpeg")
                .username("soporte")
                .horarioTrabajo("TARDE")
                .apellido("Alcantara Alvarado")
                .build();

        Usuario userLimpieza = Usuario.builder()
                .cargo(cargoLimpieza)
                .email("9oGtQ@example.com")
                .isEnabled(true)
                .password(passwordEncoder.encode(passLimpieza))
                .nombre("Maria Daniela")
                .fechaCreacion(LocalDate.now())
                .telefono("987654321")
                .imagen("cat_war01.jpeg")
                .username("limpieza")
                .horarioTrabajo("TARDE")
                .apellido("Ramirez Lopez")
                .build();

        Usuario userUser = Usuario.builder()
                .cargo(cargoUser)
                .email("jKw9G@example.com")
                .isEnabled(true)
                .password(passwordEncoder.encode(passUser))
                .nombre("Maite Ramon")
                .fechaCreacion(LocalDate.now())
                .telefono("987654321")
                .imagen("cat_war01.jpeg")
                .username("user")
                .horarioTrabajo("TARDE")
                .apellido("Mariela Sasiga")
                .build();

        return List.of(userAdmin, userMantenimiento, userSoporte, userLimpieza, userUser);
    }
}