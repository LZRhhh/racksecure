package com.lichee.racksecure.web;

import com.lichee.racksecure.config.PathConfigure;
import com.lichee.racksecure.util.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;


/**
 * 作者
 * @author jyn
 *
 */
@RestController
@RequestMapping("/api/profile")

public class ProfileController {

    @Autowired
    private PathConfigure pathConfigure;


    @PostMapping(value="/upload")
    public String upload(@RequestParam("username") String username, @RequestParam("imgStr") String imgStr){

        String basePath = this.pathConfigure.getUploadPath();
        String filePath = basePath + "/" + username + ".png";
        Base64Util.createFile(imgStr, new File(filePath));
        return "上传成功";
    }
}

