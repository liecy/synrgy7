package com.rahmi.binfood.mapper;

import com.rahmi.binfood.dto.OrderDTO;
import com.rahmi.binfood.dto.OrderDetailDTO;
import com.rahmi.binfood.exception.UserNotFoundException;
import com.rahmi.binfood.model.Order;
import com.rahmi.binfood.model.OrderDetail;
import com.rahmi.binfood.model.User;
import com.rahmi.binfood.repository.UserRepository;
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

    @Autowired
    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDTO toDto(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        // Handle nested mapping for orderDetails
        List<OrderDetailDTO> orderDetailDTOs = order.getOrderDetail().stream()
                .map(detail -> modelMapper.map(detail, OrderDetailDTO.class))
                .collect(Collectors.toList());
        orderDTO.setOrderDetails(orderDetailDTOs);
        return orderDTO;
    }

    public Order toEntity(OrderDTO orderDTO) {
        Order order = modelMapper.map(orderDTO, Order.class);
        // Handle nested mapping for orderDetails
        List<OrderDetail> orderDetails = orderDTO.getOrderDetails().stream()
                .map(detailDTO -> modelMapper.map(detailDTO, OrderDetail.class))
                .collect(Collectors.toList());
        order.setOrderDetail(orderDetails);
        return order;
    }

    public void updateFromDto(OrderDTO orderDTO, Order order) {
        modelMapper.map(orderDTO, order);
        // Handle nested mapping for orderDetails
        order.getOrderDetail().clear();
        List<OrderDetail> orderDetails = orderDTO.getOrderDetails().stream()
                .map(detailDTO -> modelMapper.map(detailDTO, OrderDetail.class))
                .collect(Collectors.toList());
        order.getOrderDetail().addAll(orderDetails);
    }
}