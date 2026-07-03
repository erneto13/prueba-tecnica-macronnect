package dev.erneto.pmn.config;

import dev.erneto.pmn.model.Usuario;
import dev.erneto.pmn.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SeedInitializer implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (usuarioRepository.count() == 0) {
            Usuario usuario = Usuario.builder()
                    .usuario("chabelo")
                    .password(passwordEncoder.encode("pepe"))
                    .build();
            usuarioRepository.save(usuario);
        }
    }
}
