package com.edu.bean;

public class Emp {
    private String name;
    private String gender;

    public Dept getDept() {
        return dept;
    }

    //表明员工属于哪个部门
    private Dept dept;

    public void setDept(Dept dept) {
        this.dept = dept;
    }


    public void setName(String name) {
        this.name = name;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                ", dept=" + dept +
                '}';
    }
}
