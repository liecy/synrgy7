package com.rahmi.binfood.controller;

import com.rahmi.binfood.dto.MerchantDTO;
import com.rahmi.binfood.mapper.MerchantMapper;
import com.rahmi.binfood.model.Merchant;
import com.rahmi.binfood.service.MerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    private MerchantService merchantService;
    private final MerchantMapper merchantMapper;

    public MerchantController(MerchantService merchantService, MerchantMapper merchantMapper) {
        this.merchantService = merchantService;
        this.merchantMapper = merchantMapper;
    }

    @PostMapping("/add")
    public ResponseEntity<MerchantDTO> addMerchant(@RequestBody MerchantDTO merchantDTO) {
        MerchantDTO newMerchantDTO = merchantService.addMerchant(merchantDTO);
        return new ResponseEntity<>(newMerchantDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MerchantDTO> updateMerchant(@PathVariable UUID id, @RequestBody MerchantDTO merchantDTO) {
        MerchantDTO updatedMerchantDTO = merchantService.updateMerchant(id, merchantDTO);
        return new ResponseEntity<>(updatedMerchantDTO, HttpStatus.OK);
    }

    @PutMapping("/toggle-status/{merchantId}")
    public ResponseEntity<String> toggleMerchantStatus(@PathVariable UUID merchantId) {
        merchantService.toggleMerchantStatus(merchantId);
        return new ResponseEntity<>("Merchant status toggled successfully", HttpStatus.OK);
    }

    @GetMapping("/open")
    public ResponseEntity<List<MerchantDTO>> getAllOpenMerchants() {
        List<MerchantDTO> openMerchantsDTO = merchantService.getAllOpenMerchants();
        return new ResponseEntity<>(openMerchantsDTO, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<MerchantDTO>> getAllMerchants(@PageableDefault(size = 3, sort = "id") Pageable pageable) {
        Page<MerchantDTO> merchantsPage = merchantService.getAllMerchants(pageable);
        return new ResponseEntity<>(merchantsPage, HttpStatus.OK);
    }
}