package dev.erneto.pmn.service;

import dev.erneto.pmn.dto.ArticuloRequestDTO;
import dev.erneto.pmn.dto.ArticuloResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticuloService {
    ArticuloResponseDTO crear(ArticuloRequestDTO dto);

    ArticuloResponseDTO actualizar(Long id, ArticuloRequestDTO dto);

    void eliminar(Long id);

    ArticuloResponseDTO obtenerPorId(Long id);

    Page<ArticuloResponseDTO> listar(Pageable pageable);
}
