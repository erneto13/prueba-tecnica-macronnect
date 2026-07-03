package dev.erneto.pmn.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotBlank(message = "Usuario obligatorio")
    private String username;

    @NotBlank(message = "Contraseña obligatoria")
    private String password;
}
