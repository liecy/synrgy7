package com.rahmi.binfood.controller;

import com.rahmi.binfood.dto.MerchantRequestDTO;
import com.rahmi.binfood.dto.MerchantResponseDTO;
import com.rahmi.binfood.mapper.MerchantMapper;
import com.rahmi.binfood.service.MerchantService;
import com.rahmi.binfood.util.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/merchants")
public class MerchantController {

    private final MerchantService merchantService;

    public MerchantController(MerchantService merchantService, MerchantMapper merchantMapper) {
        this.merchantService = merchantService;
    }

    @PreAuthorize("hasRole('MERCHANT')")
    @PostMapping("/add")
    public ResponseEntity<Object> addMerchant(@RequestBody MerchantRequestDTO merchantRequestDTO) {
        MerchantResponseDTO newMerchantResponseDTO = merchantService.addMerchant(merchantRequestDTO);
        return ApiResponse.success(HttpStatus.CREATED, "Merchant has successfully added", newMerchantResponseDTO, "merchant");
    }

    @PreAuthorize("hasRole('MERCHANT')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateMerchant(@PathVariable UUID id, @RequestBody MerchantRequestDTO merchantRequestDTO) {
        MerchantResponseDTO updatedMerchantResponseDTO = merchantService.updateMerchant(id, merchantRequestDTO);
        return ApiResponse.success(HttpStatus.OK, "Merchant has successfully updated", updatedMerchantResponseDTO, "merchant");
    }

    @PreAuthorize("hasRole('MERCHANT')")
    @PutMapping("/toggle-status/{merchantId}")
    public ResponseEntity<Object> toggleMerchantStatus(@PathVariable UUID merchantId) {
        MerchantResponseDTO toggledMerchantResponseDTO = merchantService.toggleMerchantStatus(merchantId);
        return ApiResponse.success(HttpStatus.OK, "Merchant status has been toggled successfully", toggledMerchantResponseDTO, "merchant");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/open")
    public ResponseEntity<Object> getAllOpenMerchants() {
        List<MerchantResponseDTO> openMerchantsDTO = merchantService.getAllOpenMerchants();
        return ApiResponse.success(HttpStatus.OK, "All open merchants have been retrieved successfully", openMerchantsDTO, "merchants");
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/all")
    public ResponseEntity<Object> getAllMerchants(@PageableDefault(size = 3, sort = "id") Pageable pageable) {
        Page<MerchantResponseDTO> merchantsPage = merchantService.getAllMerchants(pageable);
        return ApiResponse.success(HttpStatus.OK, "All merchants have been retrieved successfully", merchantsPage, "merchants");
    }
}