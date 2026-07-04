package dev.erneto.pmn.service;

import dev.erneto.pmn.dto.VentaRequestDTO;
import dev.erneto.pmn.dto.VentaResponseDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

public interface VentaService {

    VentaResponseDTO registrar(VentaRequestDTO dto);

    VentaResponseDTO obtenerPorId(Long id);

    Page<VentaResponseDTO> listar(Pageable pageable);

    VentaResponseDTO cancelar(Long id);
}
