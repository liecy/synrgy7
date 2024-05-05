package com.rahmi.binfood.controller;

import com.rahmi.binfood.dto.MerchantDTO;
import com.rahmi.binfood.dto.ProductDTO;
import com.rahmi.binfood.mapper.ProductMapper;
import com.rahmi.binfood.model.Product;
import com.rahmi.binfood.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
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

    @PostMapping("/add")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO newProductDTO = productService.addProduct(productDTO);
        return new ResponseEntity<>(newProductDTO, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProductDTO = productService.updateProduct(id, productDTO);
        return new ResponseEntity<>(updatedProductDTO, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

//    @GetMapping
//    public ResponseEntity<Page<ProductDTO>> getAllProducts(Pageable pageable) {
//        Page<ProductDTO> productsDTO = productService.getAllProducts(pageable);
//        return new ResponseEntity<>(productsDTO, HttpStatus.OK);
//    }

    @GetMapping("/all")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/priceGreaterThan/{minPrice}")
    public ResponseEntity<List<ProductDTO>> findProductsByPriceGreaterThan(@PathVariable Double minPrice) {
        List<ProductDTO> productsDTO = productService.findProductsByPriceGreaterThan(minPrice);
        return new ResponseEntity<>(productsDTO, HttpStatus.OK);
    }
}