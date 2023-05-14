package com.zzj.mailparse.service;


import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;

public interface MailService {

    //根据上传文件创建同名文件夹，返回文件夹地址
    String createPackage(MultipartFile file);

    //解析邮件文件，保存附件至指定文件夹中
    void parseMail(String packageName);

    //生成压缩文件夹
    void zipPackage(File filePcg, HttpServletRequest request, HttpServletResponse response);



}
