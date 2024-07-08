package com.rahmi.binfood.mapper;

import com.rahmi.binfood.dto.ProductRequestDTO;
import com.rahmi.binfood.dto.ProductResponseDTO;
import com.rahmi.binfood.dto.ProductUpdateRequestDTO;
import com.rahmi.binfood.model.Product;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product toEntity(ProductRequestDTO dto) {
        return modelMapper.map(dto, Product.class);
    }

    public ProductResponseDTO toResponseDTO(Product entity) {
        return modelMapper.map(entity, ProductResponseDTO.class);
    }

    public void updateFromDTO(ProductUpdateRequestDTO dto, Product entity) {
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
        }
//        entity.setName(dto.getName());
//        entity.setPrice(dto.getPrice());
    }

    public List<ProductResponseDTO> toResponseDTOList(List<Product> entities) {
        return entities.stream()
                .map(this::toResponseDTO)
                .collect(Collectors.toList());
    }
}