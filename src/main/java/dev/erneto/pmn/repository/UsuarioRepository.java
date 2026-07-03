package dev.erneto.pmn.repository;

import dev.erneto.pmn.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
}