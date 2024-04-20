package com.rahmi;

import com.rahmi.model.MenuItem;
import com.rahmi.model.OrderItem;
import com.rahmi.service.MenuService;
import com.rahmi.service.OrderService;
import com.rahmi.view.OrderView;
import lombok.AllArgsConstructor;

import java.io.BufferedWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

@AllArgsConstructor
public class Controller {
    private Scanner scanner;
    private MenuService menuService;
    private OrderService orderService;
    private OrderView orderView;

    public void run() {
        while (true) {
            orderView.displayMenu();
            int choice = scanner.nextInt();
            switch (choice) {
                case 0:
                    System.exit(0);
                case 99:
                    orderView.confirmOrder();
                    break;
                case 98:
                    removeOrder();
                    break;
                default:
                    if (choice > 0 && choice <= menuService.getMenuList().size()) {
                        System.out.print("Quantity: ");
                        int quantity = scanner.nextInt();
                        placeOrder(choice - 1, quantity);
                    } else {
                        System.out.println("\nInvalid choice. Please enter a number within the menu range.");
                    }
                    break;
            }
        }
    }

    private void placeOrder(int index, int quantity) {
        MenuItem menu = menuService.getMenuList().get(index);

        boolean found = false;
        for (OrderItem orderItem : orderService.getOrderList()) {
            if (orderItem.getMenuItem().equals(menu)) {
                orderItem.setQuantity(orderItem.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            orderService.addOrder(menu, quantity);
        }
    }

    private void removeOrder() {
        System.out.println("Enter the order number to remove:");
        int orderNumber = scanner.nextInt();
        orderService.removeOrder(orderNumber);
        System.out.println("Order removed successfully.");
    }
}