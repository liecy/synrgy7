package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.ProductRequestDTO;
import com.rahmi.binfood.dto.ProductResponseDTO;
import com.rahmi.binfood.dto.ProductUpdateRequestDTO;
import com.rahmi.binfood.exception.MerchantNotFoundException;
import com.rahmi.binfood.exception.ProductNotFoundException;
import com.rahmi.binfood.mapper.ProductMapper;
import com.rahmi.binfood.model.Merchant;
import com.rahmi.binfood.model.Product;
import com.rahmi.binfood.repository.MerchantRepository;
import com.rahmi.binfood.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final MerchantRepository merchantRepository;
    private final ProductMapper productMapper;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, MerchantRepository merchantRepository, ProductMapper productMapper, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.merchantRepository = merchantRepository;
        this.productMapper = productMapper;
        this.modelMapper = modelMapper;
    }

    @Override
    public ProductResponseDTO addProduct(ProductRequestDTO productRequestDTO) {
        Merchant merchant = merchantRepository.findById(productRequestDTO.getMerchantId())
                .orElseThrow(() -> new MerchantNotFoundException("Merchant not found with id: " + productRequestDTO.getMerchantId()));
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
            existingProduct.setMerchant(merchantRepository.findById(productUpdateRequestDTO.getMerchantId())
                    .orElseThrow(() -> new MerchantNotFoundException("Merchant not found with id: " + productUpdateRequestDTO.getMerchantId())));
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
        return modelMapper.map(productResponseDTO, Product.class);
    }
}