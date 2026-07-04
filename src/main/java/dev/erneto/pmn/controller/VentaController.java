package dev.erneto.pmn.controller;

import dev.erneto.pmn.dto.VentaRequestDTO;
import dev.erneto.pmn.dto.VentaResponseDTO;
import dev.erneto.pmn.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @PostMapping
    public ResponseEntity<VentaResponseDTO> registrar(@Valid @RequestBody VentaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaService.registrar(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<VentaResponseDTO>> listar(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(ventaService.listar(pageable));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<VentaResponseDTO> cancelar(@PathVariable Long id) {
        return ResponseEntity.ok(ventaService.cancelar(id));
    }
}
