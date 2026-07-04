package dev.erneto.pmn.repository;

import dev.erneto.pmn.model.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentaRepository extends JpaRepository<Venta, Long> {
    Page<Venta> findByCliente_Id(Long clienteId, Pageable pageable);

}
