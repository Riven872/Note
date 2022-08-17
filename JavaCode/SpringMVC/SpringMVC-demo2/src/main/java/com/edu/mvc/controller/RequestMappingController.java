package com.edu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
//@RequestMapping("/hello")
public class RequestMappingController {
    @RequestMapping("/testRequestMapping")
    public String success() {
        return "success";
    }

    @RequestMapping("/testPath/{id}")
    public String testPath(@PathVariable("id") int id) {
        System.out.println("id:" + id);
        return "success";
    }
}
