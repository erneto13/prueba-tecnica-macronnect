package dev.erneto.pmn.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponseDTO {
    private Long id;
    private String folio;
    private LocalDateTime fecha;
    private Long clienteId;
    private String clienteNombre;
    private String estado;
    private BigDecimal total;
    private List<DetalleVentaResponseDTO> detalles;
}
