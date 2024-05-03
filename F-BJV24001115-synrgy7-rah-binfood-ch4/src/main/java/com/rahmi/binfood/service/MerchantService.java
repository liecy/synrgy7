package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.MerchantDTO;
import com.rahmi.binfood.model.Merchant;
import org.springframework.data.domain.Page;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

public interface MerchantService {
    MerchantDTO addMerchant(MerchantDTO merchantDTO);
//    MerchantDTO updateMerchant(MerchantDTO merchantDTO);

    MerchantDTO updateMerchant(UUID id, MerchantDTO merchantDTO);

    void toggleMerchantStatus(UUID merchantId);
    List<MerchantDTO> getAllOpenMerchants();

    Page<MerchantDTO> getAllMerchants(Pageable pageable);
}