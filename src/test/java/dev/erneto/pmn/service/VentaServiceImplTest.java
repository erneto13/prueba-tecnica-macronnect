package dev.erneto.pmn.service;

import dev.erneto.pmn.dto.DetalleVentaRequestDTO;
import dev.erneto.pmn.dto.VentaRequestDTO;
import dev.erneto.pmn.dto.VentaResponseDTO;
import dev.erneto.pmn.mapper.VentaMapper;
import dev.erneto.pmn.model.Articulo;
import dev.erneto.pmn.model.Cliente;
import dev.erneto.pmn.model.DetalleVenta;
import dev.erneto.pmn.model.Venta;
import dev.erneto.pmn.repository.ArticuloRepository;
import dev.erneto.pmn.repository.ClienteRepository;
import dev.erneto.pmn.repository.VentaRepository;
import dev.erneto.pmn.service.impl.VentaServiceImpl;
import dev.erneto.pmn.util.BusinessException;
import dev.erneto.pmn.util.InsufficientStockException;
import dev.erneto.pmn.util.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VentaServiceImplTest {

    @Mock
    private VentaRepository ventaRepository;
    @Mock
    private ClienteRepository clienteRepository;
    @Mock
    private ArticuloRepository articuloRepository;

    private VentaServiceImpl ventaService;

    private Cliente cliente;
    private Articulo articulo1;
    private Articulo articulo2;

    @BeforeEach
    void setUp() {
        VentaMapper ventaMapper = new VentaMapper();
        ventaService = new VentaServiceImpl(ventaRepository, clienteRepository, articuloRepository, ventaMapper);

        cliente = Cliente.builder()
                .id(1L)
                .nombre("pepito")
                .email("pepito@gmail.com")
                .activo(true)
                .build();

        articulo1 = Articulo.builder()
                .id(1L)
                .codigo("a1")
                .nombre("lapiz 6°")
                .precio(new BigDecimal("7.21"))
                .stock(10)
                .activo(true)
                .build();

        articulo2 = Articulo.builder()
                .id(2L)
                .codigo("a2")
                .nombre("cuaderno 6°")
                .precio(new BigDecimal("15.33"))
                .stock(5)
                .activo(true)
                .build();
    }

    @Test
    void registrar_calculaTotalCorrectamenteYDescuentaStock() {
        VentaRequestDTO request = new VentaRequestDTO(1L, Arrays.asList(
                new DetalleVentaRequestDTO(1L, 2),
                new DetalleVentaRequestDTO(2L, 3)
        ));

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(articuloRepository.findById(1L)).thenReturn(Optional.of(articulo1));
        when(articuloRepository.findById(2L)).thenReturn(Optional.of(articulo2));
        when(ventaRepository.saveAndFlush(any(Venta.class))).thenAnswer(invocation -> {
            Venta v = invocation.getArgument(0);
            v.setId(100L);
            return v;
        });

        VentaResponseDTO response = ventaService.registrar(request);

        assertEquals(0, new BigDecimal("60.41").compareTo(response.getTotal()));
        assertEquals("VENTA-100", response.getFolio());
        assertEquals(8, articulo1.getStock());
        assertEquals(2, articulo2.getStock());
    }

    @Test
    void registrar_stockInsuficiente_lanzaExcepcion() {
        VentaRequestDTO request = new VentaRequestDTO(1L, Collections.singletonList(
                new DetalleVentaRequestDTO(2L, 999)
        ));

        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));
        when(articuloRepository.findById(2L)).thenReturn(Optional.of(articulo2));

        assertThrows(InsufficientStockException.class, () -> ventaService.registrar(request));
        verify(ventaRepository, never()).saveAndFlush(any());
    }

    @Test
    void registrar_clienteInexistente_lanzaExcepcion() {
        VentaRequestDTO request = new VentaRequestDTO(99L, Collections.singletonList(
                new DetalleVentaRequestDTO(1L, 1)
        ));

        when(clienteRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> ventaService.registrar(request));
    }

    @Test
    void cancelar_reponeStockYCambiaEstado() {
        DetalleVenta detalle = DetalleVenta.builder().articulo(articulo1).cantidad(4).build();
        Venta venta = Venta.builder().id(5L).folio("V-000005").cliente(cliente)
                .estado("ACTIVO").total(new BigDecimal("1000.00"))
                .detalles(Collections.singletonList(detalle)).build();

        int stockAntesDeVender = articulo1.getStock();
        articulo1.setStock(stockAntesDeVender - 4);

        when(ventaRepository.findById(5L)).thenReturn(Optional.of(venta));

        VentaResponseDTO response = ventaService.cancelar(5L);

        assertEquals("CANCELADO", response.getEstado());
        assertEquals(stockAntesDeVender, articulo1.getStock());
    }

    @Test
    void cancelar_ventaYaCancelada_lanzaExcepcion() {
        Venta venta = Venta.builder().id(5L).folio("V-000005").cliente(cliente)
                .estado("CANCELADO").total(BigDecimal.TEN)
                .detalles(Collections.emptyList()).build();

        when(ventaRepository.findById(5L)).thenReturn(Optional.of(venta));

        assertThrows(BusinessException.class, () -> ventaService.cancelar(5L));
    }
}
