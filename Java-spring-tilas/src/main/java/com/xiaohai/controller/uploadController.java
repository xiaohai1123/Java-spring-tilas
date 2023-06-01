package com.xiaohai.controller;

import com.xiaohai.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class uploadController {

    @PostMapping("/upload")
    public Result upload(String username, Integer age, @RequestParam("image") MultipartFile image) {
        log.info("文件上传:{},{},{}", username, age, image);

        //获取原始文件名
        String originalFilename = image.getOriginalFilename();

        //构造唯一文件名(不能重复) -uuid(通用唯一识别码)
        int index = originalFilename.lastIndexOf(".");
        String extname = originalFilename.substring(index);
        String newFileName = UUID.randomUUID().toString();
        log.info("新的文件名:{}",newFileName);

        //将文件存储在本地服务器的磁盘目录下
        try {
            image.transferTo(new File("G:\\软件\\python\\学习\\java_demo" +
                    "\\Java-spring-tilas\\src\\main\\resources\\static\\" + newFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Result.success();
    }
}
