package com.rahmi.binfood.order_service.mapper;

import com.rahmi.binfood.order_service.dto.OrderDTO;
import com.rahmi.binfood.order_service.dto.OrderDetailDTO;
import com.rahmi.binfood.order_service.dto.OrderRequestDTO;
import com.rahmi.binfood.order_service.model.Order;
import com.rahmi.binfood.order_service.model.OrderDetail;
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

    @Autowired
    public OrderMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public OrderDTO toDto(Order order) {
        OrderDTO orderDTO = modelMapper.map(order, OrderDTO.class);
        List<OrderDetailDTO> orderDetailDTOs = order.getOrderDetail().stream()
                .map(detail -> {
                    OrderDetailDTO detailDTO = modelMapper.map(detail, OrderDetailDTO.class);
                    detailDTO.setOrderId(order.getId());
                    detailDTO.setProductId(detail.getProductId()); // Set product ID
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
                    .collect(Collectors.toMap(detail -> detail.getId(), detail -> detail));

            List<OrderDetail> updatedDetails = orderDTO.getOrderDetails().stream()
                    .map(detailDTO -> {
                        OrderDetail detail;
                        if (existingDetails.containsKey(detailDTO.getId())) {
                            detail = existingDetails.get(detailDTO.getId());
                            detail.setQuantity(detailDTO.getQuantity());
                        } else {
                            detail = modelMapper.map(detailDTO, OrderDetail.class);
                            detail.setOrder(order);
                        }
                        detail.setTotalPrice(detail.getQuantity() * detail.getTotalPrice());
                        return detail;
                    })
                    .collect(Collectors.toList());

            order.getOrderDetail().clear();
            order.getOrderDetail().addAll(updatedDetails);
        }
    }
}

