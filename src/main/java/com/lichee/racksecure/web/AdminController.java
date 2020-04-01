package com.lichee.racksecure.web;

import com.lichee.racksecure.pojo.User;
import com.lichee.racksecure.service.UserService;
import com.lichee.racksecure.util.FaceUtil;
import com.lichee.racksecure.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

@Slf4j
@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @GetMapping("/admin")
    public String root(){
        return "/admin/index";
    }

    @GetMapping("/admin/register")
    public String register(){
        return "/admin/register";
    }

    @PostMapping("/admin/register")
    public String doRegister(User user){


        if(!userService.insert(user)){
            log.info("user exists");
            return "redirect:/admin/register?error";
        }
        log.info(user.getUsername()+" registered");
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users")
    public String listUsers(Model model){
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/listUser";
    }


    @GetMapping("/admin/users/{username}")
    public String showUser(@PathVariable String username, Model model){
        User u = userService.getByUsername(username);
        model.addAttribute("u", u);
        return "/admin/user";
    }

    @GetMapping("/admin/users/{username}/edit")
    public String editUser(@PathVariable String username, Model model){
        User u = userService.getByUsername(username);
        model.addAttribute("u", u);
        return "/admin/edit";
    }

    @PostMapping("/admin/users/{username}/face")
    public String faceUpload(@PathVariable String username,@RequestParam(name = "image") MultipartFile image) throws Exception {
        log.info("test1");
        String faceToken = saveImageAndGetFaceToken(username, image);
        User user = userService.getByUsername(username);
        updateFaceToken(user, faceToken);
        log.info("test2");
        return "redirect:/admin/users/{username}";
    }
    public String saveImageAndGetFaceToken(String username, MultipartFile image)
            throws Exception {
        File file = new File(ImageUtil.imageDirPath,username+".jpg");

        image.transferTo(file);
        BufferedImage img = ImageUtil.change2jpg(file);
        ImageIO.write(img, "jpg", file);
        String filepath = ImageUtil.imageDirPath+username+".jpg";
        String faceToken = FaceUtil.detect(filepath);
        log.info(faceToken);
        return faceToken;
    }
    public void updateFaceToken(User user, String faceToken) throws Exception {
        String oldFaceToken = user.getFaceToken();
        //从库中删除token
        if(oldFaceToken!=null) {
            FaceUtil.removeToken(oldFaceToken);
        }
        FaceUtil.addToken(faceToken);
        user.setFaceToken(faceToken);
        userService.update(user);
    }


    @PostMapping("/admin/users/{username}")
    public String deleteUser(@PathVariable String username){
//        *******************************
        return "redirect:/admin/listUser";
    }

}
