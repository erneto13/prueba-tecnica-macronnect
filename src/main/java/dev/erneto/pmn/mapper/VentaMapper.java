package dev.erneto.pmn.mapper;

import dev.erneto.pmn.dto.DetalleVentaResponseDTO;
import dev.erneto.pmn.dto.VentaResponseDTO;
import dev.erneto.pmn.model.DetalleVenta;
import dev.erneto.pmn.model.Venta;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class VentaMapper {

    public VentaResponseDTO toResponse(Venta venta) {
        List<DetalleVentaResponseDTO> detalles = venta.getDetalles().stream()
                .map(this::toDetalleResponse)
                .collect(Collectors.toList());

        return VentaResponseDTO.builder()
                .id(venta.getId())
                .folio(venta.getFolio())
                .fecha(venta.getFecha())
                .clienteId(venta.getCliente().getId())
                .clienteNombre(venta.getCliente().getNombre())
                .estado(venta.getEstado())
                .total(venta.getTotal())
                .detalles(detalles)
                .build();
    }

    private DetalleVentaResponseDTO toDetalleResponse(DetalleVenta detalle) {
        return DetalleVentaResponseDTO.builder()
                .id(detalle.getId())
                .articuloId(detalle.getArticulo().getId())
                .articuloNombre(detalle.getArticulo().getNombre())
                .cantidad(detalle.getCantidad())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getSubtotal())
                .build();
    }
}
