package com.edu.IOC03;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 学生类
 */
public class Stu {
    //数组类型属性
    private String[] array;

    //List集合类型属性
    private List<String> list;

    //Map集合类型属性
    private Map<String, String> map;

    //Set集合类型属性
    private Set<String> set;

    //对象集合类型属性
    private List<Course> courses;

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public void setArray(String[] array) {
        this.array = array;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

    public void setList(List<String> list) {
        this.list = list;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "array=" + Arrays.toString(array) +
                ", list=" + list +
                ", map=" + map +
                ", set=" + set +
                '}';
    }
}
