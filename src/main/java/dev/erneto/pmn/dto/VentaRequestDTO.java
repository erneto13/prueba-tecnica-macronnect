package dev.erneto.pmn.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequestDTO {

    @NotNull(message = "Cliente obligatorio")
    private Long clienteId;

    @NotEmpty(message = "Detalles de venta obligatorios, al menos uno")
    @Valid
    private List<DetalleVentaRequestDTO> detalles;
}
