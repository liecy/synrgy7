package com.rahmi.service;

import com.rahmi.model.MenuItem;
import com.rahmi.model.OrderItem;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.List;

public interface OrderService {
    void addOrder(MenuItem Item, int quantity);
    void removeOrder(int orderNumber);
    List<OrderItem> getOrderList();
    void clearOrderList();
    BufferedWriter saveReceipt(int total, int jumlah, String formattedTime) throws IOException;
}