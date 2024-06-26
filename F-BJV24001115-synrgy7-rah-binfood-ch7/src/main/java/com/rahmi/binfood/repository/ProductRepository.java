package com.rahmi.binfood.repository;

import com.rahmi.binfood.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    @Query("SELECT p FROM Product p WHERE p.price > :minPrice")
    List<Product> findProductsByPriceGreaterThan(@Param("minPrice") Double minPrice);
    List<Product> findByMerchantId(UUID merchantId);
}