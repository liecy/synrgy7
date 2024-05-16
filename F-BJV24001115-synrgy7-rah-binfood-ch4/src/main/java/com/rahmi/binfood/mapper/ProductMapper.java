package com.rahmi.binfood.mapper;

import com.rahmi.binfood.dto.MerchantDTO;
import com.rahmi.binfood.dto.ProductDTO;
import com.rahmi.binfood.exception.MerchantNotFoundException;
import com.rahmi.binfood.model.Merchant;
import com.rahmi.binfood.model.Product;
import com.rahmi.binfood.repository.MerchantRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

//@Component
//public class ProductMapper {
//
//    @Autowired
//    private MerchantRepository merchantRepository;
//
//    public Product toEntity(ProductDTO dto) {
//        Product product = new Product();
//        product.setId(dto.getId());
//        product.setName(dto.getName());
//        product.setPrice(dto.getPrice());
//        return product;
//    }
//
//    public ProductDTO toDTO(Product entity) {
//        ProductDTO dto = new ProductDTO();
//        dto.setId(entity.getId());
//        dto.setName(entity.getName());
//        dto.setPrice(entity.getPrice());
//        dto.setMerchantId(entity.getMerchant().getId());
//        return dto;
//    }
//
//    public Product toEntityWithMerchantId(ProductDTO dto) {
//        Product product = toEntity(dto);
//        if (dto.getMerchantId() != null) {
//            Merchant merchant = merchantRepository.findById(dto.getMerchantId())
//                    .orElseThrow(() -> new MerchantNotFoundException("Merchant not found with id: " + dto.getMerchantId()));
//            product.setMerchant(merchant);
//        }
//        return product;
//    }
//
//    public void updateFromDTO(ProductDTO dto, Product entity) {
//        entity.setName(dto.getName());
//        entity.setPrice(dto.getPrice());
//    }
//
//    public List<ProductDTO> toDTOList(List<Product> entities) {
//        return entities.stream()
//                .map(this::toDTO)
//                .collect(Collectors.toList());
//    }
//}

@Component
public class ProductMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public ProductMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Product toEntity(ProductDTO dto) {
        return modelMapper.map(dto, Product.class);
    }

    public ProductDTO toDTO(Product entity) {
        return modelMapper.map(entity, ProductDTO.class);
    }

    public void updateFromDTO(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setPrice(dto.getPrice());
    }

    public List<ProductDTO> toDTOList(List<Product> entities) {
        return entities.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
