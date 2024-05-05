package com.rahmi.binfood.repository;

import com.rahmi.binfood.model.Merchant;
import com.rahmi.binfood.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query(value = "SELECT * FROM product WHERE price > :minPrice", nativeQuery = true)
    List<Product> findProductsByPriceGreaterThan(@Param("minPrice") Double minPrice);

    @Procedure(name = "get_all_products")
    List<Product> getAllProducts();

}