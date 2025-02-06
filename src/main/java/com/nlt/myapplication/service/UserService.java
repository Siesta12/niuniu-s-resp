package com.nlt.myapplication.service;

import com.nlt.myapplication.pojo.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {

    User login(User user);
    void register(User user);
    String uploadImage(MultipartFile image) throws IOException;
    byte[] downloadImage(String filename) throws IOException;

}
