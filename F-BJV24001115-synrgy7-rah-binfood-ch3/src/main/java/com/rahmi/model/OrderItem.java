package com.rahmi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderItem {
    private MenuItem menuItem;
    private int quantity;

    public int getTotalPrice() {
        return menuItem.getPrice() * quantity;
    }
}