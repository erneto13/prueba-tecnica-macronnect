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
import org.springframework.data.domain.Pageable;

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
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        clienteMapper.updateEntity(cliente, dto);
        return clienteMapper.toResponse(clienteRepository.save(cliente));
    }

    @Override
    public void eliminar(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        cliente.setActivo(false);
        clienteRepository.save(cliente);
    }

    @Override
    public ClienteResponseDTO buscarPorId(Long id) {
        Cliente cliente = clienteRepository.findById(id).orElseThrow();
        return clienteMapper.toResponse(cliente);
    }

    @Override
    public Page<ClienteResponseDTO> paginar(Pageable pageable) {
        return clienteRepository.findAll(pageable).map(clienteMapper::toResponse);
    }
}
