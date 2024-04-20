package com.rahmi.service;

import com.rahmi.model.MenuItem;
import com.rahmi.model.OrderItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    private OrderService orderService;

    @BeforeEach
    void setUp() {
        orderService = new OrderServiceImpl();
    }

    @Test
    void addOrder() {
        MenuItem menuItem = new MenuItem("Nasi Goreng", 15000);
        orderService.addOrder(menuItem, 2);
        List<OrderItem> orderList = orderService.getOrderList();
        Assertions.assertEquals(1, orderList.size());
        OrderItem addedOrderItem = orderList.get(0);
        Assertions.assertEquals(menuItem, addedOrderItem.getMenuItem());
        Assertions.assertEquals(2, addedOrderItem.getQuantity());
    }

    @Test
    void removeOrder() {
        MenuItem menuItem1 = new MenuItem("Nasi Goreng", 15000);
        MenuItem menuItem2 = new MenuItem("Mie Goreng", 13000);
        orderService.addOrder(menuItem1, 1);
        orderService.addOrder(menuItem2, 2);

        orderService.removeOrder(0); // Remove first order
        List<OrderItem> orderList = orderService.getOrderList();
        Assertions.assertEquals(1, orderList.size());
        Assertions.assertEquals(menuItem2, orderList.get(0).getMenuItem());
    }

    @Test
    void getOrderList() {
        MenuItem menuItem1 = new MenuItem("Nasi Goreng", 15000);
        MenuItem menuItem2 = new MenuItem("Mie Goreng", 13000);
        orderService.addOrder(menuItem1, 1);
        orderService.addOrder(menuItem2, 2);

        List<OrderItem> orderList = orderService.getOrderList();
        Assertions.assertEquals(2, orderList.size());
        Assertions.assertEquals(menuItem1, orderList.get(0).getMenuItem());
        Assertions.assertEquals(menuItem2, orderList.get(1).getMenuItem());
    }

    @Test
    void clearOrderList() {
        MenuItem menuItem1 = new MenuItem("Nasi Goreng", 15000);
        MenuItem menuItem2 = new MenuItem("Mie Goreng", 13000);
        orderService.addOrder(menuItem1, 1);
        orderService.addOrder(menuItem2, 2);

        orderService.clearOrderList();
        List<OrderItem> orderList = orderService.getOrderList();
        Assertions.assertTrue(orderList.isEmpty());
    }

    @Test
    void saveReceipt() throws IOException {
        MenuItem menuItem1 = new MenuItem("Nasi Goreng", 15000);
        MenuItem menuItem2 = new MenuItem("Mie Goreng", 13000);
        orderService.addOrder(menuItem1, 1);
        orderService.addOrder(menuItem2, 2);

        BufferedWriter writer = orderService.saveReceipt(41000, 3, "2024-04-20 12:00:00");
        writer.close();
    }
}