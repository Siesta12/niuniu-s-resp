package com.nlt.myapplication.controller;

import com.nlt.myapplication.pojo.Response;
import com.nlt.myapplication.pojo.User;
import com.nlt.myapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public Response login(@RequestBody User user) {
        User user1 = userService.login(user);
        if (user1 != null) {
            System.out.println(user + "用户登录");
            return Response.success();
        }
        return Response.error("用户名或密码错误");
    }

    @PostMapping("/register")
    public Response register(@RequestBody User user) {
        try {
            userService.register(user);
        } catch (Exception e) {
            return Response.error("用户名已存在");
        }
        System.out.println(user + "用户注册");
        return Response.success();
    }

    @PostMapping("/upload")
    public Response uploadImage(@RequestParam("image") MultipartFile image) {
        System.out.println("正在上传图片");
        try {
            String imageUrl = userService.uploadImage(image);
            return Response.success(imageUrl);
            //测试用
//            return Response.success("/uploads/1.jpg");
        } catch (Exception e) {
            return Response.error(e.getMessage());
        }
    }

    @GetMapping("/download/{filename}")
    public Response downloadImage(@PathVariable("filename") String filename) {
        System.out.println(filename);
        byte[] imageBytes;
        try {
            imageBytes = userService.downloadImage(filename);
        } catch (IOException e) {
            return Response.error(e.getMessage());
        }
        return Response.success(imageBytes);
    }

}
