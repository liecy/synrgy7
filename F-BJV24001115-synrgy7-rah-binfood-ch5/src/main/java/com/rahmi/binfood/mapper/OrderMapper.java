package com.rahmi.binfood.mapper;

import com.rahmi.binfood.dto.OrderDTO;
import com.rahmi.binfood.dto.OrderDetailDTO;
import com.rahmi.binfood.exception.UserNotFoundException;
import com.rahmi.binfood.model.Order;
import com.rahmi.binfood.model.OrderDetail;
import com.rahmi.binfood.model.Product;
import com.rahmi.binfood.model.User;
import com.rahmi.binfood.repository.UserRepository;
import com.rahmi.binfood.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

//@Service
//public class OrderMapper {
//
//    private final ModelMapper modelMapper;
//
//    @Autowired
//    public OrderMapper(ModelMapper modelMapper) {
//        this.modelMapper = modelMapper;
//    }
//
//    public OrderDTO toDto(Order order) {
//        return modelMapper.map(order, OrderDTO.class);
//    }
//
//    public Order toEntity(OrderDTO orderDTO) {
//        return modelMapper.map(orderDTO, Order.class);
//    }
//}

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
                    detailDTO.setOrderId(order.getId()); // Set orderId here
                    return detailDTO;
                })
                .collect(Collectors.toList());
        orderDTO.setOrderDetails(orderDetailDTOs);
        return orderDTO;
    }

    public Order toEntity(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        List<OrderDetail> orderDetails = orderDTO.getOrderDetails().stream()
                .map(detailDTO -> {
                    OrderDetail detail = modelMapper.map(detailDTO, OrderDetail.class);
                    Product product = productService.getProductById(detailDTO.getProductId());
                    detail.setTotalPrice(product.getPrice() * detailDTO.getQuantity());
                    detail.setOrder(order); // Set the order in order detail
                    return detail;
                })
                .collect(Collectors.toList());
        order.setOrderDetail(orderDetails);
        return order;
    }

    public OrderDetailDTO mapToOrderDetailDTO(OrderDetail orderDetail) {
        return modelMapper.map(orderDetail, OrderDetailDTO.class);
    }

    public OrderDetail mapToOrderDetail(OrderDetailDTO orderDetailDTO) {
        return modelMapper.map(orderDetailDTO, OrderDetail.class);
    }

    public void updateFromDto(OrderDTO orderDTO, Order order) {
        modelMapper.map(orderDTO, order);
        order.getOrderDetail().clear();
        List<OrderDetail> orderDetails = orderDTO.getOrderDetails().stream()
                .map(detailDTO -> {
                    OrderDetail detail = modelMapper.map(detailDTO, OrderDetail.class);
                    Product product = productService.getProductById(detailDTO.getProductId());
                    detail.setTotalPrice(product.getPrice() * detailDTO.getQuantity());
                    detail.setOrder(order); // Set the order in order detail
                    return detail;
                })
                .toList();
        order.getOrderDetail().addAll(orderDetails);
    }
}