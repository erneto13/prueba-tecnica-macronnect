package dev.erneto.pmn.service.impl;

import dev.erneto.pmn.dto.ClienteRequestDTO;
import dev.erneto.pmn.dto.ClienteResponseDTO;
import dev.erneto.pmn.mapper.ClienteMapper;
import dev.erneto.pmn.model.Cliente;
import dev.erneto.pmn.repository.ClienteRepository;
import dev.erneto.pmn.service.ClienteService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    @Override
    public ClienteResponseDTO crear(ClienteRequestDTO dto) {
        Cliente cliente = clienteMapper.toEntity(dto);
        System.out.println(cliente);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public ClienteResponseDTO actualizar(Long id, ClienteRequestDTO dto) {
        return null;
    }

    @Override
    public void eliminar(Long id) {

    }

    @Override
    public ClienteResponseDTO buscarPorId(Long id) {
        return null;
    }

    @Override
    public Page<ClienteResponseDTO> paginar(Pageable pageable) {
        return null;
    }
}
