package com.edu.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class tableController {
    @GetMapping("/basic_table")
    public String basic_table() {
        return "/table/basic_table";
    }

    @GetMapping("/dynamic_table")
    public String dynamic_table() {
        return "table/dynamic_table";
    }

    @GetMapping("/responsive_table")
    public String responsive_table() {
        return "table/responsive_table";
    }

    @GetMapping("/editable_table")
    public String editable_table() {
        return "table/editable_table";
    }
}
