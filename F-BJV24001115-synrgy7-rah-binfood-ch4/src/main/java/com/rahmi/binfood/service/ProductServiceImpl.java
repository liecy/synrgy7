package com.rahmi.binfood.service;

import com.rahmi.binfood.model.Product;
import com.rahmi.binfood.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(Product product) {
        // logic
        return productRepository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        // logic
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(UUID productId) {
        // logic
        productRepository.deleteById(productId);
    }

    @Override
    public List<Product> getAllAvailableProducts() {
        return productRepository.findByOpenTrue();
    }
}