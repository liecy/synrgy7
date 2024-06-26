package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.MerchantRequestDTO;
import com.rahmi.binfood.dto.MerchantResponseDTO;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

public interface MerchantService {
    MerchantResponseDTO addMerchant(MerchantRequestDTO merchantRequestDTO);
    MerchantResponseDTO updateMerchant(UUID id, MerchantRequestDTO merchantRequestDTO);
    MerchantResponseDTO toggleMerchantStatus(UUID merchantId);
    List<MerchantResponseDTO> getAllOpenMerchants();
    Page<MerchantResponseDTO> getAllMerchants(Pageable pageable);
}