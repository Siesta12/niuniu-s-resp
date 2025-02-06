package com.nlt.myapplication.service.impl;

import com.nlt.myapplication.mapper.UserMapper;
import com.nlt.myapplication.pojo.User;
import com.nlt.myapplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    private static final String UPLOAD_DIR = "src/main/resources/static/uploads/";
    private static final String PYTHON_SCRIPT_PATH = "src/main/resources/script/denoise_script.py";

    @Autowired
    UserMapper userMapper;

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }

    @Override
    public void register(User user) {
        try {
            userMapper.register(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String uploadImage(MultipartFile image) throws IOException {
        // 生成唯一的文件名
        String fileName = UUID.randomUUID() + "_" + image.getOriginalFilename();

        // 创建目标文件路径
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 保存文件到指定目录
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(image.getInputStream(), filePath);

        // 调用 Python 脚本进行降噪处理，并覆盖原图
        try {
            // 构造 Python 命令
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "python",
                    PYTHON_SCRIPT_PATH,
                    "-i",
                    filePath.toString()
            );

            // 重定向错误流
            processBuilder.redirectErrorStream(true);

            // 启动进程
            Process process = processBuilder.start();

            // 读取输出
            java.io.BufferedReader reader = new java.io.BufferedReader(
                    new java.io.InputStreamReader(process.getInputStream())
            );
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("Python Script Output: " + line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Python 脚本运行失败，退出码: " + exitCode);
            } else {
                System.out.println("Python 脚本运行成功，图片已降噪并覆盖原图");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("图片降噪失败", e);
        }

        // 返回图片的访问 URL
        return "/uploads/" + fileName;
    }

    @Override
    public byte[] downloadImage(String filename) throws IOException {
        try {
            Path path = Paths.get(UPLOAD_DIR + filename);
            File file = path.toFile();
            if (!file.exists()) {
                throw new IOException("File not found: " + filename);
            }
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new IOException("File not found: " + filename);
        }
    }

}
