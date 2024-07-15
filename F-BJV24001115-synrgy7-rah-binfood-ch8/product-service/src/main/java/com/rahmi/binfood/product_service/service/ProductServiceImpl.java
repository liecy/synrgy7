package com.rahmi.binfood.product_service.service;

import com.rahmi.binfood.product_service.client.MerchantClient;
import com.rahmi.binfood.product_service.dto.*;
import com.rahmi.binfood.product_service.exception.MerchantNotFoundException;
import com.rahmi.binfood.product_service.exception.ProductNotFoundException;
import com.rahmi.binfood.product_service.mapper.ProductMapper;
import com.rahmi.binfood.product_service.model.Merchant;
import com.rahmi.binfood.product_service.model.Product;
import com.rahmi.binfood.product_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;
    private final MerchantClient merchantClient;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper, MerchantClient merchantClient) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
        this.merchantClient = merchantClient;
    }

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        MerchantResponseDTO merchantResponse = merchantClient.getMerchantById(productRequestDTO.getMerchantId());
        if (merchantResponse == null) {
            throw new MerchantNotFoundException("Merchant not found with id: " + productRequestDTO.getMerchantId());
        }
        Merchant merchant = new Merchant();
        merchant.setId(merchantResponse.getId());
        merchant.setName(merchantResponse.getName());

        Product product = productMapper.toEntity(productRequestDTO);
        product.setMerchant(merchant);
        Product savedProduct = productRepository.save(product);
        return productMapper.toResponseDTO(savedProduct);
    }

    @Override
    public ProductResponseDTO updateProduct(UUID id, ProductUpdateRequestDTO productUpdateRequestDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        productMapper.updateFromDTO(productUpdateRequestDTO, existingProduct);
        if (productUpdateRequestDTO.getMerchantId() != null) {
            MerchantResponseDTO merchantResponse = merchantClient.getMerchantById(productUpdateRequestDTO.getMerchantId());
            if (merchantResponse == null) {
                throw new MerchantNotFoundException("Merchant not found with id: " + productUpdateRequestDTO.getMerchantId());
            }
            Merchant merchant = new Merchant();
            merchant.setId(merchantResponse.getId());
            merchant.setName(merchantResponse.getName());
            existingProduct.setMerchant(merchant);
        }
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toResponseDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponseDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toResponseDTOList(products);
    }

    @Override
    public List<ProductResponseDTO> findProductsByPriceGreaterThan(Double minPrice) {
        List<Product> products = productRepository.findProductsByPriceGreaterThan(minPrice);
        return productMapper.toResponseDTOList(products);
    }

    @Override
    public List<ProductResponseDTO> getProductsByMerchant(UUID merchantId) {
        List<Product> products = productRepository.findByMerchantId(merchantId);
        return productMapper.toResponseDTOList(products);
    }

    @Override
    public Product getProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productId));
    }

    @Override
    public ProductResponseDTO getProductResponseDTOById(UUID productId) {
        Product product = getProductById(productId);
        return productMapper.toResponseDTO(product);
    }

    @Override
    public Product convertToProduct(ProductResponseDTO productResponseDTO) {
        return productMapper.toEntity(productResponseDTO);
    }

    @Override
    public void updateProductQuantity(ProductQuantityUpdateDTO productQuantityUpdateDTO) {
        Product product = productRepository.findById(productQuantityUpdateDTO.getProductId())
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + productQuantityUpdateDTO.getProductId()));
        product.setQuantity(product.getQuantity() - productQuantityUpdateDTO.getQuantity());
        productRepository.save(product);
    }
}
