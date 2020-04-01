package com.lichee.racksecure.web;

import com.lichee.racksecure.dao.RackDAO;
import com.lichee.racksecure.pojo.Rack;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@AllArgsConstructor
public class RackController {


    private final RackDAO rackDAO;

    @GetMapping("/racks")
    public String listRacks(Model model){
        List<Rack> racks = rackDAO.findAll();
        model.addAttribute("racks", racks);
        return "/listRack";
    }


}
