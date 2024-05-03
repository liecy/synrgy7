package com.rahmi.binfood.mapper;

import com.rahmi.binfood.dto.MerchantDTO;
import com.rahmi.binfood.model.Merchant;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MerchantMapper {

    public Merchant toEntity(MerchantDTO dto) {
        Merchant merchant = new Merchant();
        merchant.setId(dto.getId());
        merchant.setName(dto.getName());
        merchant.setLocation(dto.getLocation());
        merchant.setOpen(dto.getOpen());
        return merchant;
    }

    public MerchantDTO toDTO(Merchant entity) {
        MerchantDTO dto = new MerchantDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setLocation(entity.getLocation());
        dto.setOpen(entity.getOpen()); // Menggunakan getOpen() untuk properti open
        return dto;
    }

    public void updateFromDTO(MerchantDTO dto, Merchant entity) {
        entity.setName(dto.getName());
        entity.setLocation(dto.getLocation());
        // Tidak perlu memperbarui properti open karena sudah diatur di toEntity() atau toDTO()
    }

    public List<MerchantDTO> toDTOList(List<Merchant> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}