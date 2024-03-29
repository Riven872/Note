package com.edu.IOC01;

public class User {
    private Integer age;
    private String name;
    private String birthday;

    private void add() {
        System.out.println("我是User类的add方法");
    }

    public User() {
    }

    public User(Integer age, String name, String birthday) {
        this.age = age;
        this.name = name;
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                '}';
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void testDemo (){
        System.out.println("年龄：" + this.age + " 姓名：" + this.name);
    }
}
