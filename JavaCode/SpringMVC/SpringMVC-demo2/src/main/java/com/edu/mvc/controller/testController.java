package com.edu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class testController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/param")
    public String param() {
        return "test_param";
    }
}
