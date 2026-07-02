package dev.erneto.pmn.repository;

import dev.erneto.pmn.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
