package com.rahmi.binfood.mapper;

import com.rahmi.binfood.dto.OrderDTO;
import com.rahmi.binfood.model.Order;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDTO toDto(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    public Order toEntity(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }
}