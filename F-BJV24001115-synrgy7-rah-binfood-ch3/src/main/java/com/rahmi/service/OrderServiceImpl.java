package com.rahmi.service;

import com.rahmi.model.MenuItem;
import com.rahmi.model.OrderItem;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private List<OrderItem> orderList = new ArrayList<>();

    @Override
    public void addOrder(MenuItem menuItem, int quantity) {
        orderList.add(new OrderItem(menuItem, quantity));
    }

    @Override
    public void removeOrder(int orderNumber) {
        if (orderNumber >= 0 && orderNumber < orderList.size()) {
            orderList.remove(orderNumber);
        } else {
            System.out.println("Invalid order number.");
        }
    }

    @Override
    public List<OrderItem> getOrderList() {
        return orderList;
    }

    @Override
    public void clearOrderList() {
        orderList.clear();
    }

    @Override
    public BufferedWriter saveReceipt(int total, int jumlah, String formattedTime) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter("receipt.txt"));
        writer.write("===============================\n");
        writer.write("            BinarFud            \n");
        writer.write("===============================\n");
        writer.write("\n");
        writer.write(formattedTime + "\n");
        writer.write("Thank you for order in\nBinarFud\n");
        writer.write("\n");
        writer.write("Below are your orders:\n");
        writer.write("\n");

        for (OrderItem orderItem : orderList) {
            MenuItem menuItem = orderItem.getMenuItem();
            int quantity = orderItem.getQuantity();
            int subtotal = menuItem.getPrice() * quantity;
            writer.write(menuItem.getName() + "\t" + quantity + "\t" + subtotal + "\n");
        }
        writer.write("------------------------------+\n");

        writer.write("Total" + "\t\t" + jumlah + "\t" + total + "\n");
        writer.write("\n");
        writer.write("Payment : BinarCash\n");
        writer.write("\n");
        writer.write("===============================\n");
        writer.write("Save this receipt as proof of\npayment\n");
        writer.write("===============================\n");
        return writer;
    }
}