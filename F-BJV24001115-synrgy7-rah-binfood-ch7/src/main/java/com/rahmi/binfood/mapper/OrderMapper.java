package com.rahmi.binfood.mapper;

import com.rahmi.binfood.dto.OrderDTO;
import com.rahmi.binfood.dto.OrderDetailDTO;
import com.rahmi.binfood.dto.OrderRequestDTO;
import com.rahmi.binfood.dto.ProductResponseDTO;
import com.rahmi.binfood.model.Order;
import com.rahmi.binfood.model.OrderDetail;
import com.rahmi.binfood.model.Product;
import com.rahmi.binfood.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    private final ModelMapper modelMapper;
    private final ProductService productService;

    @Autowired
    public OrderMapper(ModelMapper modelMapper, ProductService productService) {
        this.modelMapper = modelMapper;
        this.productService = productService;
    }

    public OrderDTO toDto(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        List<OrderDetailDTO> orderDetailDTOs = order.getOrderDetail().stream()
                .map(detail -> {
                    OrderDetailDTO detailDTO = modelMapper.map(detail, OrderDetailDTO.class);
                    detailDTO.setOrderId(order.getId());
                    if (detail.getProduct() != null) { // Ensure Product is not null
                        detailDTO.setProductName(detail.getProduct().getName());
                        detailDTO.setProductId(detail.getProduct().getId());
                        detailDTO.setPrice(detail.getProduct().getPrice());
                    } else {
                        // Log if Product is null
                        System.out.println("Product is null for OrderDetail ID: " + detail.getId());
                    }
                    return detailDTO;
                })
                .collect(Collectors.toList());
        orderDTO.setOrderDetails(orderDetailDTOs);
        orderDTO.setCreatedDate(order.getTime());
        return orderDTO;
    }

    public Order toEntity(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        List<OrderDetail> orderDetails = orderDTO.getOrderDetails().stream()
                .map(detailDTO -> {
                    OrderDetail detail = modelMapper.map(detailDTO, OrderDetail.class);
                    ProductResponseDTO productResponseDTO = productService.getProductResponseDTOById(detailDTO.getProductId());
                    Product product = productService.convertToProduct(productResponseDTO);
                    detail.setProduct(product); // Ensure Product is set
                    detail.setTotalPrice(product.getPrice() * detailDTO.getQuantity());
                    detail.setOrder(order);
                    return detail;
                })
                .collect(Collectors.toList());
        order.setOrderDetail(orderDetails);
        return order;
    }

    public void updateFromDto(OrderRequestDTO orderDTO, Order order) {
        if (orderDTO.getDestinationAddress() != null) {
            order.setDestinationAddress(orderDTO.getDestinationAddress());
        }
        if (orderDTO.getCompleted() != null) {
            order.setCompleted(orderDTO.getCompleted());
        }
        if (orderDTO.getOrderDetails() != null) {
            Map<UUID, OrderDetail> existingDetails = order.getOrderDetail().stream()
                    .collect(Collectors.toMap(detail -> detail.getProduct().getId(), detail -> detail));

            List<OrderDetail> updatedDetails = orderDTO.getOrderDetails().stream()
                    .map(detailDTO -> {
                        OrderDetail detail;
                        if (existingDetails.containsKey(detailDTO.getProductId())) {
                            detail = existingDetails.get(detailDTO.getProductId());
                            detail.setQuantity(detailDTO.getQuantity());
                        } else {
                            detail = modelMapper.map(detailDTO, OrderDetail.class);
                            ProductResponseDTO productResponseDTO = productService.getProductResponseDTOById(detailDTO.getProductId());
                            Product product = productService.convertToProduct(productResponseDTO);
                            detail.setProduct(product);
                            detail.setOrder(order);
                        }
                        detail.setTotalPrice(detail.getProduct().getPrice() * detailDTO.getQuantity());
                        return detail;
                    })
                    .collect(Collectors.toList());

            order.getOrderDetail().clear();
            order.getOrderDetail().addAll(updatedDetails);
        }
    }
}