package com.rahmi.service;

import com.rahmi.model.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MenuServiceImpl implements MenuService {
    @Override
    public List<MenuItem> getMenuList() {
        List<MenuItem> menuList = new ArrayList<>();
        menuList.add(new MenuItem("Nasi Goreng", 15000));
        menuList.add(new MenuItem("Mie Goreng", 13000));
        menuList.add(new MenuItem("Nasi Ayam", 18000));
        menuList.add(new MenuItem("Es Teh Manis", 3000));
        menuList.add(new MenuItem("Es Jeruk", 5000));
        return menuList;
    }
}