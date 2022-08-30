package com.edu.boot.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@ResponseBody
//@Controller

@RestController
public class HelloController {
    @RequestMapping("foo")
    public String handler01() {
        return "foobar";
    }
}
