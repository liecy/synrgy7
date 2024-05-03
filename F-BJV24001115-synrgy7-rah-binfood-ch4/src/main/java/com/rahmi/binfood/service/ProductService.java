package com.rahmi.binfood.service;

import com.rahmi.binfood.model.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    Product addProduct(Product product);
    Product updateProduct(Product product);
    void deleteProduct(UUID productId);
    List<Product> getAllAvailableProducts();
}
