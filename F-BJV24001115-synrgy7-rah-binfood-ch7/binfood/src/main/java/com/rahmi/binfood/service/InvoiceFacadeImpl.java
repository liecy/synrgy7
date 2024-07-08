package com.rahmi.binfood.service;

import com.rahmi.binfood.dto.OrderDTO;
import com.rahmi.binfood.dto.OrderDetailDTO;
import com.rahmi.binfood.dto.UserDTO;
import com.rahmi.binfood.exception.OrderNotFoundException;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class InvoiceFacadeImpl implements InvoiceFacade {

    private final OrderService orderService;
    private final OrderDetailService orderDetailService;
    private final UserService userService;

    @Autowired
    public InvoiceFacadeImpl(OrderService orderService, OrderDetailService orderDetailService, UserService userService) {
        this.orderService = orderService;
        this.orderDetailService = orderDetailService;
        this.userService = userService;
    }

    @Override
    public byte[] generateInvoice(UUID orderId) throws JRException {

        try {
            OrderDTO order = orderService.getOrderById(orderId);
            if (order == null) {
                throw new OrderNotFoundException("Order not found with id: " + orderId);
            }

            UserDTO user = userService.getUserById(order.getUserId());

            List<OrderDetailDTO> orderDetails = orderDetailService.getOrderDetailsByOrderId(orderId);

            int totalQuantity = orderDetails.stream().mapToInt(OrderDetailDTO::getQuantity).sum();
            double totalPrice = orderDetails.stream().mapToDouble(OrderDetailDTO::getTotalPrice).sum();

            InputStream templateStream = this.getClass().getResourceAsStream("/jasper/order-list-report.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(orderDetails);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("username", user.getUsername());
            parameters.put("orderDate", order.getCreatedDate());
            parameters.put("destinationAddress", order.getDestinationAddress());
            parameters.put("totalQuantity", totalQuantity);
            parameters.put("totalPrice", totalPrice);
            parameters.put("datasource", dataSource);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            throw new RuntimeException("Failed to generate invoice", e);
        }
    }
}