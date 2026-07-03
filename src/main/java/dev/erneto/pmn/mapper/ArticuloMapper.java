package dev.erneto.pmn.mapper;

import dev.erneto.pmn.dto.ArticuloRequestDTO;
import dev.erneto.pmn.dto.ArticuloResponseDTO;
import dev.erneto.pmn.model.Articulo;
import org.springframework.stereotype.Component;

@Component
public class ArticuloMapper {

    public Articulo toEntity(ArticuloRequestDTO dto) {
        return Articulo.builder()
                .codigo(dto.getCodigo())
                .nombre(dto.getNombre())
                .descripcion(dto.getDescripcion())
                .precio(dto.getPrecio())
                .stock(dto.getStock())
                .build();
    }

    public void updateEntity(Articulo articulo, ArticuloRequestDTO dto) {
        articulo.setCodigo(dto.getCodigo());
        articulo.setNombre(dto.getNombre());
        articulo.setDescripcion(dto.getDescripcion());
        articulo.setPrecio(dto.getPrecio());
        articulo.setStock(dto.getStock());
    }

    public ArticuloResponseDTO toResponse(Articulo articulo) {
        return ArticuloResponseDTO.builder()
                .id(articulo.getId())
                .codigo(articulo.getCodigo())
                .nombre(articulo.getNombre())
                .descripcion(articulo.getDescripcion())
                .precio(articulo.getPrecio())
                .stock(articulo.getStock())
                .activo(articulo.getActivo())
                .build();
    }
}
