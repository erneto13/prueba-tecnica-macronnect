package dev.erneto.pmn.service.impl;

import dev.erneto.pmn.dto.ArticuloRequestDTO;
import dev.erneto.pmn.dto.ArticuloResponseDTO;
import dev.erneto.pmn.mapper.ArticuloMapper;
import dev.erneto.pmn.model.Articulo;
import dev.erneto.pmn.repository.ArticuloRepository;
import dev.erneto.pmn.service.ArticuloService;
import dev.erneto.pmn.util.DuplicateResourceException;
import dev.erneto.pmn.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ArticuloServiceImpl implements ArticuloService {

    private final ArticuloRepository articuloRepository;
    private final ArticuloMapper articuloMapper;

    @Override
    public ArticuloResponseDTO crear(ArticuloRequestDTO dto) {
        if (articuloRepository.existsByCodigo(dto.getCodigo())) {
            throw new DuplicateResourceException("ERROR :: Artículo con código existente " + dto.getCodigo());
        }
        Articulo articulo = articuloMapper.toEntity(dto);
        return articuloMapper.toResponse(articuloRepository.save(articulo));
    }

    @Override
    public ArticuloResponseDTO actualizar(Long id, ArticuloRequestDTO dto) {
        Articulo articulo = buscarActivoPorId(id);
        if (articuloRepository.existsByCodigoAndIdNot(dto.getCodigo(), id)) {
            throw new DuplicateResourceException("ERROR :: Artículo con código existente " + dto.getCodigo());
        }
        articuloMapper.updateEntity(articulo, dto);
        return articuloMapper.toResponse(articuloRepository.save(articulo));
    }

    @Override
    public void eliminar(Long id) {
        Articulo articulo = buscarActivoPorId(id);
        articulo.setActivo(false);
        articuloRepository.save(articulo);
    }

    @Override
    @Transactional(readOnly = true)
    public ArticuloResponseDTO obtenerPorId(Long id) {
        return articuloMapper.toResponse(buscarActivoPorId(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ArticuloResponseDTO> listar(Pageable pageable) {
        return articuloRepository.findByActivoTrue(pageable).map(articuloMapper::toResponse);
    }

    private Articulo buscarActivoPorId(Long id) {
        Articulo articulo = articuloRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("ERROR :: Articulo no encontrado con ID: " + id));
        if (!Boolean.TRUE.equals(articulo.getActivo())) {
            throw new ResourceNotFoundException("ERROR :: Articulo no encontrado con ID: " + id);
        }
        return articulo;
    }
}
