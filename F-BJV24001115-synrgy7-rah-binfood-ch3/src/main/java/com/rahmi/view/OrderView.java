package com.rahmi.view;

import com.rahmi.model.MenuItem;
import com.rahmi.model.OrderItem;
import com.rahmi.service.MenuService;
import com.rahmi.service.OrderService;
import lombok.AllArgsConstructor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class OrderView {
    private Scanner scanner;
    private MenuService menuService;
    private OrderService orderService;

    public void displayMenu() {
        System.out.println("\n============================");
        System.out.println(" Welcome to BinarFud ");
        System.out.println("============================\n");
        System.out.println("Please select your food:");

        List<MenuItem> menuList = menuService.getMenuList();
        for (int i = 0; i < menuList.size(); i++) {
            MenuItem menu = menuList.get(i);
            System.out.println((i + 1) + ". " + menu.getName() + "\t\t| " + menu.getPrice());
        }

        System.out.println("----------------------------");
        System.out.println("99. Place Order & Pay");
        System.out.println("98. Remove Order");
        System.out.println("0.  Exit the application\n");
        System.out.print("=> ");
    }

    public void printReceipt(int total, int itemCount) {
        LocalDateTime currentTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedTime = currentTime.format(formatter);

        System.out.println("\n===============================");
        System.out.println("         BinarFud            ");
        System.out.println("===============================\n");
        System.out.println(formattedTime);
        System.out.println("Thank you for ordering from\nBinarFud\n");
        System.out.println("Below is your order\n");

        List<OrderItem> orderList = orderService.getOrderList();
        for (OrderItem orderItem : orderList) {
            MenuItem menu = orderItem.getMenuItem();
            int quantity = orderItem.getQuantity();
            int subtotal = orderItem.getTotalPrice();
            System.out.println(menu.getName() + "\t" + quantity + "\t\t" + subtotal);
        }

        System.out.println("------------------------------+");
        System.out.println("Total" + "\t\t\t" + itemCount + "\t\t" + total + "\n");
        System.out.println("Payment: BinarCash\n");
        System.out.println("===============================");
        System.out.println("Keep this receipt as payment\nproof");
        System.out.println("===============================\n");

        try {
            BufferedWriter writer = orderService.saveReceipt(total, itemCount, formattedTime);
            writer.close();
            System.out.println("Receipt has been saved in the file receipt.txt");
        } catch (IOException e) {
            System.out.println("Failed to save receipt.");
        }
    }

    public void confirmOrder() {
        System.out.println("\n==============================");
        System.out.println(" Confirmation & Payment ");
        System.out.println("==============================\n");

        List<OrderItem> orderList = orderService.getOrderList();
        int total = 0;
        int itemCount = 0;
        for (OrderItem orderItem : orderList) {
            MenuItem menu = orderItem.getMenuItem();
            int quantity = orderItem.getQuantity();
            int subtotal = menu.getPrice() * quantity;
            System.out.println(menu.getName() + "\t" + quantity + "\t\t" + subtotal);
            total += subtotal;
            itemCount += quantity;
        }

        System.out.println("------------------------------+");
        System.out.println("Total" + "\t\t\t" + itemCount + "\t\t" + total + "\n");
        System.out.println("1. Confirm and Pay");
        System.out.println("2. Back to main menu");
        System.out.println("0. Exit the application\n");
        System.out.print("=> ");

        int choice = scanner.nextInt();
        if (choice == 1) {
            printReceipt(total, itemCount);
            orderService.clearOrderList();
        } else if (choice == 0) {
            System.exit(0);
        }
    }
}
