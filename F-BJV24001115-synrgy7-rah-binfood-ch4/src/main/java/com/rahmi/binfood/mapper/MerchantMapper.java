package com.rahmi.binfood.mapper;

import com.rahmi.binfood.dto.MerchantDTO;
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

    public Merchant toEntity(MerchantDTO dto) {
        return modelMapper.map(dto, Merchant.class);
    }

    public MerchantDTO toDTO(Merchant entity) {
        return modelMapper.map(entity, MerchantDTO.class);
    }

    public void updateFromDTO(MerchantDTO dto, Merchant entity) {
//        modelMapper.map(dto, entity);
        entity.setName(dto.getName());
        entity.setLocation(dto.getLocation());
//        entity.setOpen(dto.getOpen());
    }

    public List<MerchantDTO> toDTOList(List<Merchant> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}

//@Component
//public class MerchantMapper {
//
//    public Merchant toEntity(MerchantDTO dto) {
//        Merchant merchant = new Merchant();
//        merchant.setId(dto.getId());
//        merchant.setName(dto.getName());
//        merchant.setLocation(dto.getLocation());
//        merchant.setOpen(dto.getOpen());
//        return merchant;
//    }
//
//    public MerchantDTO toDTO(Merchant entity) {
//        MerchantDTO dto = new MerchantDTO();
//        dto.setId(entity.getId());
//        dto.setName(entity.getName());
//        dto.setLocation(entity.getLocation());
//        dto.setOpen(entity.getOpen());
//        return dto;
//    }
//
//    public void updateFromDTO(MerchantDTO dto, Merchant entity) {
//        entity.setName(dto.getName());
//        entity.setLocation(dto.getLocation());
//    }
//
//    public List<MerchantDTO> toDTOList(List<Merchant> entities) {
//        return entities.stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }
//}