package com.rahmi.binfood.mapper;

import com.rahmi.binfood.dto.OrderDetailDTO;
import com.rahmi.binfood.model.OrderDetail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailMapper {

    private final ModelMapper modelMapper;

    @Autowired
    public OrderDetailMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDetailDTO toDto(OrderDetail orderDetail) {
        return modelMapper.map(orderDetail, OrderDetailDTO.class);
    }

    public OrderDetail toEntity(OrderDetailDTO orderDetailDTO) {
        return modelMapper.map(orderDetailDTO, OrderDetail.class);
    }
}