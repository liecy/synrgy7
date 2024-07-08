package com.rahmi.binfood.mapper;

import com.rahmi.binfood.dto.MerchantRequestDTO;
import com.rahmi.binfood.dto.MerchantResponseDTO;
import com.rahmi.binfood.model.Merchant;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MerchantMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public MerchantMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Merchant toEntity(MerchantRequestDTO dto) {
        return modelMapper.map(dto, Merchant.class);
    }

    public MerchantResponseDTO toDTO(Merchant entity) {
        return modelMapper.map(entity, MerchantResponseDTO.class);
    }

    public void updateFromDTO(MerchantRequestDTO dto, Merchant entity) {
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getLocation() != null) {
            entity.setLocation(dto.getLocation());
        }
    }

    public List<MerchantResponseDTO> toDTOList(List<Merchant> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}