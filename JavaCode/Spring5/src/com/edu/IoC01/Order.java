package com.edu.IoC01;

public class Order {
    private int id;
    private String name;

    public Order(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void testOrderDemo() {
        System.out.println("id：" + this.id + " 名称:" + this.name);
    }
}
