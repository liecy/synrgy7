package com.rahmi.binfood.service;

import com.rahmi.binfood.model.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {
    public void sendFCM(List<Product> productList) {
        StringBuilder message = new StringBuilder("Promo Produk: \n");
        for (Product product : productList) {
            message.append(product.getName()).append(" - ").append(product.getPrice()).append("\n");
        }
        System.out.println("Promotion sent to FCM: " + message.toString());
    }

    public void sendToMail() {
        System.out.println("Promotion sent via email");
    }
}