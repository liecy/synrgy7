package com.rahmi;

import com.rahmi.service.MenuService;
import com.rahmi.service.MenuServiceImpl;
import com.rahmi.service.OrderService;
import com.rahmi.service.OrderServiceImpl;
import com.rahmi.view.OrderView;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final MenuService menuService = new MenuServiceImpl();
    private static final OrderService orderService = new OrderServiceImpl();

    public static void main(String[] args) {
        OrderView orderView = new OrderView(scanner, menuService, orderService);
        Controller controller = new Controller(scanner, menuService, orderService, orderView);
        controller.run();
    }
}