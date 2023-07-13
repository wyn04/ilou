package com.yinan.iloubackend.service;

import com.qiniu.common.QiniuException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IQiniuServiceTest {

    @Resource
    private IQiniuService qiniuService;

    @Test
    void uploadFile() throws QiniuException {
        String result = qiniuService.uploadFile(new File("/Users/wangyinan/Downloads/IMG_9147.JPG"), "IMG_9147.JPG");
        System.out.println("访问地址： " + result);
    }

    @Test
    void testUploadFile() {
    }

    @Test
    void delete() {
    }
}