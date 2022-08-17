package com.edu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ParamController {
    @RequestMapping("/testServletAPI")
    public String testServletAPI(HttpServletRequest request) {
        String username = request.getParameter("username");
        String pwd = request.getParameter("password");
        System.out.println("username: " + username + " pwd: " + pwd);
        return "success";
    }
}
