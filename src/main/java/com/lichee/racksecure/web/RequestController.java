package com.lichee.racksecure.web;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RequestController {

//    private final RequestService requestService;

    @PostMapping("/request")
    public String request(){


        return "redirect:/racks";
    }
}
