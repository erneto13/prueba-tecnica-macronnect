package dev.erneto.pmn.controller;

import dev.erneto.pmn.dto.ArticuloRequestDTO;
import dev.erneto.pmn.dto.ArticuloResponseDTO;
import dev.erneto.pmn.service.ArticuloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articulos")
@RequiredArgsConstructor
public class ArticuloController {

    private final ArticuloService articuloService;

    @PostMapping
    public ResponseEntity<ArticuloResponseDTO> crear(@Valid @RequestBody ArticuloRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(articuloService.crear(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticuloResponseDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ArticuloRequestDTO dto) {
        return ResponseEntity.ok(articuloService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        articuloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArticuloResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(articuloService.obtenerPorId(id));
    }

    @GetMapping
    public ResponseEntity<Page<ArticuloResponseDTO>> listar(@PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(articuloService.listar(pageable));
    }
}
