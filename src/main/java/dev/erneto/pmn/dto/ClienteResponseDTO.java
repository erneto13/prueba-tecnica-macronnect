package dev.erneto.pmn.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteResponseDTO {
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private Boolean activo;
}

