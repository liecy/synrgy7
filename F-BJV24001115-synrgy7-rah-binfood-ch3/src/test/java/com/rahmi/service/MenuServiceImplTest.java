package com.rahmi.service;

import com.rahmi.model.MenuItem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MenuServiceImplTest {
    private MenuService menuService;

    @BeforeEach
    public void setUp() {
        menuService = new MenuServiceImpl();
    }

    @Test
    public void testGetMenuList() {
        List<MenuItem> menuList = menuService.getMenuList();
        // Assert that the menu list is not null
        Assertions.assertNotNull(menuList);
        // Assert that the menu list contains some items
        Assertions.assertFalse(menuList.isEmpty());
        // Assert that the menu list contains expected menu items
        Assertions.assertEquals(5, menuList.size());
        // You can add more assertions to check individual menu items if needed
        // For example:
        MenuItem firstMenuItem = menuList.get(0);
        Assertions.assertEquals("Nasi Goreng", firstMenuItem.getName());
        Assertions.assertEquals(15000, firstMenuItem.getPrice());
        // Add similar assertions for other menu items
    }
}