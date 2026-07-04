package dev.erneto.pmn.service.impl;

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
import dev.erneto.pmn.service.VentaService;
import dev.erneto.pmn.util.BusinessException;
import dev.erneto.pmn.util.InsufficientStockException;
import dev.erneto.pmn.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class VentaServiceImpl implements VentaService {

    private final VentaRepository ventaRepository;
    private final ClienteRepository clienteRepository;
    private final ArticuloRepository articuloRepository;
    private final VentaMapper ventaMapper;

    @Override
    public VentaResponseDTO registrar(VentaRequestDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .filter(Cliente::getActivo)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + dto.getClienteId()));

        Venta venta = Venta.builder()
                .cliente(cliente)
                .fecha(LocalDateTime.now())
                .estado("ACTIVO")
                .total(BigDecimal.ZERO)
                .build();

        BigDecimal total = BigDecimal.ZERO;

        for (DetalleVentaRequestDTO detalleDto : dto.getDetalles()) {
            Articulo articulo = articuloRepository.findById(detalleDto.getArticuloId())
                    .filter(Articulo::getActivo)
                    .orElseThrow(() -> new ResourceNotFoundException("Articulo no encontrado con ID: " + detalleDto.getArticuloId()));

            if (articulo.getStock() < detalleDto.getCantidad()) {
                throw new InsufficientStockException("Stock insuficiente para el articulo: " + articulo.getCodigo()
                        + " (disponible: " + articulo.getStock() + ", solicitado: " + detalleDto.getCantidad() + ")");
            }

            articulo.setStock(articulo.getStock() - detalleDto.getCantidad());

            BigDecimal subtotal = articulo.getPrecio().multiply(BigDecimal.valueOf(detalleDto.getCantidad()));
            total = total.add(subtotal);

            DetalleVenta detalle = DetalleVenta.builder()
                    .articulo(articulo)
                    .cantidad(detalleDto.getCantidad())
                    .precioUnitario(articulo.getPrecio())
                    .subtotal(subtotal)
                    .build();

            venta.addDetalle(detalle);
        }

        venta.setTotal(total);

        Venta guardada = ventaRepository.saveAndFlush(venta);
        guardada.setFolio(String.format("VENTA-%03d", guardada.getId()));

        return ventaMapper.toResponse(guardada);
    }

    @Override
    @Transactional(readOnly = true)
    public VentaResponseDTO obtenerPorId(Long id) {
        return ventaMapper.toResponse(buscarPorId(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<VentaResponseDTO> listar(Pageable pageable) {
        return ventaRepository.findAll(pageable).map(ventaMapper::toResponse);
    }

    @Override
    public VentaResponseDTO cancelar(Long id) {
        Venta venta = buscarPorId(id);

        if (venta.getEstado() == "CANCELADO") {
            throw new BusinessException("La venta con folio " + venta.getFolio() + " ya se encuentra cancelada");
        }

        for (DetalleVenta detalle : venta.getDetalles()) {
            Articulo articulo = detalle.getArticulo();
            articulo.setStock(articulo.getStock() + detalle.getCantidad());
        }

        venta.setEstado("CANCELADO");
        return ventaMapper.toResponse(venta);
    }

    private Venta buscarPorId(Long id) {
        return ventaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Venta no encontrada con ID: " + id));
    }
}