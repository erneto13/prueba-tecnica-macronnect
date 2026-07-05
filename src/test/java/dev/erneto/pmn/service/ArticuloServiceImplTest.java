package dev.erneto.pmn.service;

import dev.erneto.pmn.dto.ArticuloRequestDTO;
import dev.erneto.pmn.dto.ArticuloResponseDTO;
import dev.erneto.pmn.mapper.ArticuloMapper;
import dev.erneto.pmn.model.Articulo;
import dev.erneto.pmn.repository.ArticuloRepository;
import dev.erneto.pmn.service.impl.ArticuloServiceImpl;
import dev.erneto.pmn.util.DuplicateResourceException;
import dev.erneto.pmn.util.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ArticuloServiceImplTest {

    @Mock
    private ArticuloRepository articuloRepository;

    private ArticuloServiceImpl articuloService;

    @BeforeEach
    void setUp() {
        ArticuloMapper articuloMapper = new ArticuloMapper();
        articuloService = new ArticuloServiceImpl(articuloRepository, articuloMapper);
    }

    @Test
    void crear_codigoDuplicado_lanzaExcepcion() {
        ArticuloRequestDTO dto = new ArticuloRequestDTO(
                "abc-1",
                "casco rojo",
                "casco rojo grande",
                new BigDecimal("3342.342"),
                3);

        when(articuloRepository.existsByCodigo("abc-1")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> articuloService.crear(dto));
        verify(articuloRepository, never()).save(any());
    }

    @Test
    void crear_codigoValido_guardaArticulo() {
        ArticuloRequestDTO dto = new ArticuloRequestDTO(
                "abc-2",
                "retrovisor negro",
                "retrovisor negro izq",
                new BigDecimal("300.50"),
                17
        );

        Articulo guardado = Articulo.builder()
                .id(1L)
                .codigo("abc-2")
                .nombre("retrovisor negro")
                .descripcion("retrovisor negro izq")
                .precio(new BigDecimal("300.50"))
                .stock(17)
                .activo(true)
                .build();

        when(articuloRepository.existsByCodigo("abc-2")).thenReturn(false);
        when(articuloRepository.save(any(Articulo.class))).thenReturn(guardado);

        ArticuloResponseDTO response = articuloService.crear(dto);

        assertEquals("abc-2", response.getCodigo());
        assertEquals(17, response.getStock());
    }

    @Test
    void obtenerPorId_articuloInactivo_lanzaExcepcion() {
        Articulo inactivo = Articulo.builder().id(1L).codigo("abc-3").activo(false).build();

        when(articuloRepository.findById(1L)).thenReturn(Optional.of(inactivo));

        assertThrows(ResourceNotFoundException.class, () -> articuloService.obtenerPorId(1L));
    }
}
