package com.zzj.mailparse.control;

import com.zzj.mailparse.model.RespEnum;
import com.zzj.mailparse.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


@Controller
@RequestMapping("/mail")
public class MailControl {

    private Logger Log = LoggerFactory.getLogger(MailControl.class);

    @Autowired
    private MailService mailService;

    /*
       初始页
     */
    @GetMapping("/start")
    public String startPage(){
        return "mailparse";
    }

    /*
        上传文件并获得压缩文件夹
     */
    @PostMapping("/upload")
    @ResponseBody
    public String uploadFile(@RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //1、根据上传文件名创建同名文件夹
        String directoryPath = mailService.createPackage(file);
        if (directoryPath == null){
            return RespEnum.FILE_ERROR.getMessage();
        }
        //获得文件夹
        File filePcg = new File(directoryPath);

        //2、解析邮件，将附件邮件存入该文件夹中


        //3、压缩该文件夹并返回前端
        mailService.zipPackage(filePcg,request,response);


        return directoryPath;
    }



}
