package com.rahmi.binfood.controller;

import com.rahmi.binfood.dto.ProductRequestDTO;
import com.rahmi.binfood.dto.ProductResponseDTO;
import com.rahmi.binfood.dto.ProductUpdateRequestDTO;
import com.rahmi.binfood.service.ProductService;
import com.rahmi.binfood.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PreAuthorize("hasRole('MERCHANT') or hasRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<Object> addProduct(@RequestBody ProductRequestDTO productRequestDTO) {
        ProductResponseDTO newProductDTO = productService.addProduct(productRequestDTO);
        return ApiResponse.success(HttpStatus.CREATED, "Product has successfully added", newProductDTO, "product");
    }

    @PreAuthorize("hasRole('MERCHANT') or hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateProduct(@PathVariable UUID id, @RequestBody ProductUpdateRequestDTO productUpdateRequestDTO) {
        ProductResponseDTO updatedProductDTO = productService.updateProduct(id, productUpdateRequestDTO);
        return ApiResponse.success(HttpStatus.OK, "Product has successfully updated", updatedProductDTO, "product");
    }

    @PreAuthorize("hasRole('USER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ApiResponse.success(HttpStatus.ACCEPTED, "Product has successfully deleted", null, "product");
    }

    @PreAuthorize("hasRole('USER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Object> getAllProducts() {
        List<ProductResponseDTO> productsDTO = productService.getAllProducts();
        return ApiResponse.success(HttpStatus.OK, "Products have been successfully retrieved", productsDTO, "products");
    }

    @PreAuthorize("hasRole('USER') or hasRole('MERCHANT') or hasRole('ADMIN')")
    @GetMapping("/priceGreaterThan/{minPrice}")
    public ResponseEntity<Object> findProductsByPriceGreaterThan(@PathVariable Double minPrice) {
        List<ProductResponseDTO> productsDTO = productService.findProductsByPriceGreaterThan(minPrice);
        return ApiResponse.success(HttpStatus.OK, "Products have been successfully retrieved", productsDTO, "products");
    }

    @PreAuthorize("hasRole('MERCHANT') or hasRole('ADMIN')")
    @GetMapping("/merchant/{merchantId}")
    public ResponseEntity<Object> getProductsByMerchant(@PathVariable UUID merchantId) {
        List<ProductResponseDTO> productsDTO = productService.getProductsByMerchant(merchantId);
        return ApiResponse.success(HttpStatus.OK, "Products have been successfully retrieved", productsDTO, "products");
    }
}