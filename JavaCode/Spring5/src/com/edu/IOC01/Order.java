package com.edu.IOC01;

public class Order {
    private int id;
    private String name;
    private double price;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }


    public Order(int id, String name) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public void testOrderDemo() {
        System.out.println("id：" + this.id + " 名称:" + this.name);
    }
}
