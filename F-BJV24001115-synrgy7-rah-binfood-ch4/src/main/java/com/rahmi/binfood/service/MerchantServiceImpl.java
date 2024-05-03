package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.MerchantDTO;
import com.rahmi.binfood.exception.MerchantNotFoundException;
import com.rahmi.binfood.mapper.MerchantMapper;
import com.rahmi.binfood.model.Merchant;
import com.rahmi.binfood.repository.MerchantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;

@Service
public class MerchantServiceImpl implements MerchantService {

    private final MerchantRepository merchantRepository;
    private final MerchantMapper merchantMapper;

    private static final Logger logger = LoggerFactory.getLogger(MerchantServiceImpl.class);

    @Autowired
    public MerchantServiceImpl(MerchantRepository merchantRepository, MerchantMapper merchantMapper) {
        this.merchantRepository = merchantRepository;
        this.merchantMapper = merchantMapper;
    }

    @Override
    public MerchantDTO addMerchant(MerchantDTO merchantDTO) {
        logger.debug("Adding new merchant: {}", merchantDTO.getName());
        Merchant merchant = merchantMapper.toEntity(merchantDTO);
        if (merchant.getName() == null || merchant.getName().isEmpty()) {
            throw new IllegalArgumentException("Merchant name cannot be empty");
        }
        Merchant savedMerchant = merchantRepository.save(merchant);
        return merchantMapper.toDTO(savedMerchant);
    }

    @Override
    public MerchantDTO updateMerchant(UUID id, MerchantDTO merchantDTO) {
        Merchant existingMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> new MerchantNotFoundException("Merchant not found with id: " + id));

        merchantMapper.updateFromDTO(merchantDTO, existingMerchant);
        Merchant updatedMerchant = merchantRepository.save(existingMerchant);
        return merchantMapper.toDTO(updatedMerchant);
    }

    @Override
    public void toggleMerchantStatus(UUID id) {
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new MerchantNotFoundException("Merchant not found with id: " + id));
        merchant.setOpen(!merchant.getOpen());
        merchantRepository.save(merchant);
    }

    @Override
    public List<MerchantDTO> getAllOpenMerchants() {
        List<Merchant> openMerchants = merchantRepository.findByOpen(true);

        return merchantMapper.toDTOList(openMerchants);
    }

    @Override
    public Page<MerchantDTO> getAllMerchants(Pageable pageable) {
        Page<Merchant> merchantPage = merchantRepository.findAll(pageable);
        return merchantPage.map(merchantMapper::toDTO);
    }
}
