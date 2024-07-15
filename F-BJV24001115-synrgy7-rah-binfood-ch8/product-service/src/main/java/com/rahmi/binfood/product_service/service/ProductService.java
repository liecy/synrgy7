package com.rahmi.binfood.product_service.service;

import com.rahmi.binfood.product_service.dto.ProductQuantityUpdateDTO;
import com.rahmi.binfood.product_service.dto.ProductRequestDTO;
import com.rahmi.binfood.product_service.dto.ProductResponseDTO;
import com.rahmi.binfood.product_service.dto.ProductUpdateRequestDTO;
import com.rahmi.binfood.product_service.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO);
    ProductResponseDTO updateProduct(UUID id, ProductUpdateRequestDTO productUpdateRequestDTO);
    void deleteProduct(UUID id);
    List<ProductResponseDTO> getAllProducts();
    List<ProductResponseDTO> findProductsByPriceGreaterThan(Double minPrice);
    List<ProductResponseDTO> getProductsByMerchant(UUID merchantId);
    Product getProductById(UUID productId);
    ProductResponseDTO getProductResponseDTOById(UUID productId);
    Product convertToProduct(ProductResponseDTO productResponseDTO);
    void updateProductQuantity(ProductQuantityUpdateDTO productQuantityUpdateDTO);
}
