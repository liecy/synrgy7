package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.MerchantDTO;
import com.rahmi.binfood.dto.ProductDTO;
import com.rahmi.binfood.model.Product;
import org.springframework.data.domain.Page;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductDTO addProduct(ProductDTO productDTO);

    ProductDTO updateProduct(UUID id, ProductDTO productDTO);

    void deleteProduct(UUID id);

    //    Page<ProductDTO> getAllProducts(Pageable pageable);
    List<ProductDTO> getAllProducts();

    List<ProductDTO> findProductsByPriceGreaterThan(Double minPrice);
}