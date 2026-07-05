package dev.erneto.pmn.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticuloRequestDTO {

    @NotBlank(message = "Código obligatorio")
    private String codigo;

    @NotBlank(message = "Nombre obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "Precio obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio debe ser mayor a 0")
    private BigDecimal precio;

    @NotNull(message = "Stock obligatorio")
    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock;
}
