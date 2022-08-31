package com.edu.boot.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@ResponseBody
//@Controller

@Slf4j
@RestController
public class HelloController {
    @RequestMapping("foo")
    public String handler01() {
        return "foobar";
    }
}
