package com.zzj.mailparse.service.impl;

import com.zzj.mailparse.service.MailService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.URLEncoder;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MailServiceImplTest {

    @Autowired
    private MailService mailService;

    @Test
    void createPackage() throws IOException {

        //创建模拟IO文件
        MockMultipartFile file = new MockMultipartFile(
                "test.eml", // original file name
                "test.eml", // file name in bytes
                "text/plain", // content type
                "test data".getBytes()); // file content

        //调用方法并返回创建文件夹的地址值
        String directoryPath = mailService.createPackage(file);

        //判断地址值不为空
        assertNotNull(directoryPath);

        //判断文件夹存在
        File directory = new File(directoryPath);
        assertTrue(directory.exists());

        //判断文件夹名称与文件一致
        String[] allSpellFileNames = file.getOriginalFilename().split("\\.");
        String expectedDirectoryName = allSpellFileNames[0];
        assertEquals(expectedDirectoryName, directory.getName());

        //判断文件夹是否为空
        assertEquals(0, directory.listFiles().length);

    }

    @Test
    void zipPackage() throws Exception {
        //创建虚拟文件夹及文件
        String directoryPath = "test_directory";
        File directory = new File(directoryPath);
        directory.mkdir();
        File file1 = new File(directory, "file1.txt");
        file1.createNewFile();
        File file2 = new File(directory, "file2.txt");
        file2.createNewFile();

        //创建虚拟http反映对象
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        //调用压缩方法
        mailService.zipPackage(directory, request, response);

        //http响应成功
        assertEquals(HttpStatus.OK.value(), response.getStatus());

        //压缩文件名是否一致
        String expectedHeader = "attachment;fileName=" + URLEncoder.encode(directory.getName() + ".zip", "UTF-8");
        assertEquals(expectedHeader, response.getHeader("Content-Disposition"));

        // assert that the response content is not empty
        byte[] content = response.getContentAsByteArray();
        assertTrue(content.length > 0);

        //文件成功删除
        assertFalse(directory.exists());
    }
}