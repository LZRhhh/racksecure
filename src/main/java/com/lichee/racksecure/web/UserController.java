package com.lichee.racksecure.web;

import com.lichee.racksecure.pojo.Rack;
import com.lichee.racksecure.pojo.Request;
import com.lichee.racksecure.pojo.User;
import com.lichee.racksecure.service.RackService;
import com.lichee.racksecure.service.RequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;


@Controller
public class UserController {

    @Autowired
    RequestService requestService;

    @Autowired
    RackService rackService;

    @GetMapping("/user")
    public String user() throws Exception{
        return "/user/index";
    }

    @GetMapping("user/profile")
    public String profile(){
        return "/user/profile";
    }

    @GetMapping("user/request")
    public String request(Model model){
        List<Rack> racks = rackService.findAll();
        model.addAttribute("racks", racks);
        return "/user/request";
    }

    @PostMapping("user/request")
    public String addRequest(HttpServletRequest httpServletRequest, Request request){

        HttpSession session = httpServletRequest.getSession();
        User user = (User) session.getAttribute("user");
        request.setUsername(user.getUsername());
        System.out.println(request.getStart());
        System.out.println(request.getEnd());
        requestService.add(request);
        return "redirect:/user";
    }

}
