package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.MerchantDTO;
import com.rahmi.binfood.dto.ProductDTO;
import com.rahmi.binfood.exception.ProductNotFoundException;
import com.rahmi.binfood.mapper.MerchantMapper;
import com.rahmi.binfood.mapper.ProductMapper;
import com.rahmi.binfood.model.Merchant;
import com.rahmi.binfood.model.Product;
import com.rahmi.binfood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = productMapper.toEntity(productDTO);
        Product savedProduct = productRepository.save(product);
        return productMapper.toDTO(savedProduct);
    }

    @Override
    public ProductDTO updateProduct(UUID id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
        productMapper.updateFromDTO(productDTO, existingProduct);
        Product updatedProduct = productRepository.save(existingProduct);
        return productMapper.toDTO(updatedProduct);
    }

    @Override
    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

//    @Override
//    public Page<ProductDTO> getAllProducts(Pageable pageable) {
//        Page<Product> products = productRepository.findAll(pageable);
//        return products.map(productMapper::toDTO);
//    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<ProductDTO> findProductsByPriceGreaterThan(Double minPrice) {
        List<Product> products = productRepository.findProductsByPriceGreaterThan(minPrice);
        return products.stream()
                .map(productMapper::toDTO)
                .collect(Collectors.toList());
    }
}