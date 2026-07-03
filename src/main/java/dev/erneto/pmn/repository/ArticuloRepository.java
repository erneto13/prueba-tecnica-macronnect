package dev.erneto.pmn.repository;

import dev.erneto.pmn.model.Articulo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ArticuloRepository extends JpaRepository<Articulo, Long> {
    Page<Articulo> findByActivoTrue(Pageable pageable);

    boolean existsByCodigo(String codigo);

    boolean existsByCodigoAndIdNot(String codigo, Long id);

    Optional<Articulo> findByCodigo(String codigo);
}
