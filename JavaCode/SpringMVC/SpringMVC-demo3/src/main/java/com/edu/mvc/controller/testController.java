package com.edu.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class testController {
    @RequestMapping("/")
    public String index() {
        return "index";
    }

    @RequestMapping("/testModelAndView")
    public ModelAndView testModelAndView() {
        ModelAndView mav = new ModelAndView();
        //处理数据模型，即向请求域request共享数据
        mav.addObject("testRequestScope", "hello, ModelAndView");
        //设置视图名称
        mav.setViewName("success");
        return mav;
    }

    @RequestMapping("/testModel")
    public String testModel(Model model) {
        model.addAttribute("testScope", "hello,Model");
        return "success";
    }

    @RequestMapping("/test_view")
    public String testView() {
        return "test_view";
    }
}
