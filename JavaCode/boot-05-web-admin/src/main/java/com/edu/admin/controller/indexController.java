package com.edu.admin.controller;

import com.edu.admin.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class indexController {

    /**
     * 登录页
     *
     * @return
     */
    @GetMapping(value = {"/", "/login"})
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String main(User user, HttpSession httpSession, Model model) {
        if (StringUtils.hasLength(user.getUserName()) && "123".equals(user.getPassword())) {
            //把登录成功的用户保存起来
            httpSession.setAttribute("loginUser", user);

            //登录成功，重定向到main.html，否则地址栏还是login
            //重定向，防止表单重复提交
            return "redirect:/main.html";
        } else {
            //登录失败，返回到登录页
            model.addAttribute("msg", "账号密码错误");
            return "login";
        }
    }

    /**
     * 去main页面，有后缀意味着页面跳转
     * 在刷新main.html页面时，请求名也是main.html，会执行该请求并跳转到main页面，否则无法直接访问main页面
     *
     * @return
     */
    @GetMapping("/main.html")
    public String mainPage(HttpSession session, Model model) {
        ////使用拦截器、过滤器等判断是否登录
        //Object loginUser = session.getAttribute("loginUser");
        //if (loginUser != null) {
        //    return "main";
        //} else {
        //    //登录失败，返回到登录页
        //    model.addAttribute("msg", "请重新登录");
        //    return "login";
        //}
    }
}
