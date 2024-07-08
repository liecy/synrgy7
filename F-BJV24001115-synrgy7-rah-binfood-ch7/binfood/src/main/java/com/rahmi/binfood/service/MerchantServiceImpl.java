package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.MerchantRequestDTO;
import com.rahmi.binfood.dto.MerchantResponseDTO;
import com.rahmi.binfood.exception.MerchantNotFoundException;
import com.rahmi.binfood.mapper.MerchantMapper;
import com.rahmi.binfood.model.Merchant;
import com.rahmi.binfood.repository.MerchantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
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
    public MerchantResponseDTO addMerchant(MerchantRequestDTO merchantRequestDTO) {
        logger.debug("Adding new merchant: {}", merchantRequestDTO.getName());
        Merchant merchant = merchantMapper.toEntity(merchantRequestDTO);
        if (merchant.getName() == null || merchant.getName().isEmpty()) {
            throw new IllegalArgumentException("Merchant name cannot be empty");
        }
        if (merchant.getOpen() == null) {
            merchant.setOpen(false); // Ensuring open is false by default
        }
        Merchant savedMerchant = merchantRepository.save(merchant);
        return merchantMapper.toDTO(savedMerchant);
    }

    @Override
    public MerchantResponseDTO updateMerchant(UUID id, MerchantRequestDTO merchantRequestDTO) {
        logger.debug("Updating merchant with id: {}", id);
        Merchant existingMerchant = merchantRepository.findById(id)
                .orElseThrow(() -> new MerchantNotFoundException("Merchant not found with id: " + id));

        merchantMapper.updateFromDTO(merchantRequestDTO, existingMerchant);
        Merchant updatedMerchant = merchantRepository.save(existingMerchant);
        return merchantMapper.toDTO(updatedMerchant);
    }

    @Override
    public MerchantResponseDTO toggleMerchantStatus(UUID id) {
        logger.debug("Toggling status for merchant with id: {}", id);
        Merchant merchant = merchantRepository.findById(id)
                .orElseThrow(() -> new MerchantNotFoundException("Merchant not found with id: " + id));
        merchant.setOpen(!merchant.getOpen());
        Merchant updatedMerchant = merchantRepository.save(merchant);
        return merchantMapper.toDTO(updatedMerchant);
//        return modelMapper.map(updatedMerchant, MerchantResponseDTO.class); // Mengembalikan MerchantResponseDTO
    }

    @Override
    public List<MerchantResponseDTO> getAllOpenMerchants() {
        logger.debug("Fetching all open merchants");
        List<Merchant> openMerchants = merchantRepository.findByOpen(true);
        return merchantMapper.toDTOList(openMerchants);
    }

    @Override
    public Page<MerchantResponseDTO> getAllMerchants(Pageable pageable) {
        logger.debug("Fetching all merchants with pagination");
        Page<Merchant> merchantPage = merchantRepository.findAll(pageable);
        return merchantPage.map(merchantMapper::toDTO);
    }
}
