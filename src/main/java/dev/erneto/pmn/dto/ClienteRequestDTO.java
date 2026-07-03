package dev.erneto.pmn.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteRequestDTO {

    @NotBlank(message = "Nombre obligatorio")
    private String nombre;

    @NotBlank(message = "Email obligatorio")
    @Email(message = "Email inválido")
    private String email;

    private String telefono;

    private String direccion;
}
