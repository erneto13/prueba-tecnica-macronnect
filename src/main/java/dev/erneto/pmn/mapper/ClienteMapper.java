package dev.erneto.pmn.mapper;

import dev.erneto.pmn.dto.ClienteRequestDTO;
import dev.erneto.pmn.dto.ClienteResponseDTO;
import dev.erneto.pmn.model.Cliente;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {

    public Cliente toEntity(ClienteRequestDTO dto) {
        return Cliente.builder()
                .nombre(dto.getNombre())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .activo(true)
                .build();
    }

    public void updateEntity(Cliente cliente, ClienteRequestDTO dto) {
        cliente.setNombre(dto.getNombre());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
    }

    public ClienteResponseDTO toResponse(Cliente cliente) {
        return ClienteResponseDTO.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .activo(cliente.getActivo())
                .build();
    }
}

