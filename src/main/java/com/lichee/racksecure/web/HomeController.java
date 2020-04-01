package com.lichee.racksecure.web;


import com.lichee.racksecure.config.SysLog;
import com.lichee.racksecure.pojo.User;
import com.lichee.racksecure.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;

@Slf4j
@Controller
@AllArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping({"/", "/index", "/home"})
    public String root(){
        return "index";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/home";
    }

    @SysLog(value = "User Login")
    @PostMapping("/login")
    public Object login(@RequestParam("username") String username, @RequestParam("password") String password, Model model, HttpSession session) {
        if(0==username.length() || 0==password.length()){
            model.addAttribute("error", "Please input username and password");
            return "login";
        }
        username = HtmlUtils.htmlEscape(username);
        User user = userService.get(username,password);
        if(user==null){
            try {
                User u = userService.getByUsername(username);
                log.info(username + "tried to login.");
                log.info("Password: " + u.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }
            model.addAttribute("error", "Wrong username or password");
            return "login";
        }
        session.setAttribute("user", user);
        if(username.equals("admin")){
            return "redirect:/admin";
        }
        return "redirect:/user";
    }

    @GetMapping("/hello")
    public String hello(){
        return "hello";
    }


}
