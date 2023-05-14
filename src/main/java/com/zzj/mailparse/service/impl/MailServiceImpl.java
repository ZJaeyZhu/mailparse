package com.zzj.mailparse.service.impl;

import com.zzj.mailparse.model.RespEnum;
import com.zzj.mailparse.service.MailService;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.net.URLEncoder;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class MailServiceImpl implements MailService {

    //同名文件夹创建路径
    private static final String FRONT_DIRECTORY_PATH = "src/main/resources/static/creatdirectories/";

    private Logger Log = LoggerFactory.getLogger(MailServiceImpl.class);


    /*
     * 根据上传文件创建同名文件夹，返回文件夹地址
     */
    @Override
    public String createPackage(MultipartFile file) {
        //获得完整文件名（加后缀）
        String allSpellFileName = file.getOriginalFilename();
        Log.info("本次上传的文件名为："+allSpellFileName);
        //文件名为空报错，返回空
        if (allSpellFileName.isEmpty()){
            Log.info(RespEnum.FILE_ERROR.getMessage());
            return null;
        }
        //获得不加后缀的文件名，作为文件夹名称
        String[] allSpellFileNames = allSpellFileName.split("\\.");
        String fileName = allSpellFileNames[0];
        //不为eml文件报错，返回空
        if (!allSpellFileNames[1].equals("eml")){
            Log.info(RespEnum.FILE_ERROR.getMessage());
            return null;
        }
        //创建文件夹，用于存放邮件附件文件
        File filePackage = new File(FRONT_DIRECTORY_PATH+fileName);
        filePackage.mkdir();
        Log.info("文件夹创建成功");
        Log.info(filePackage.getAbsolutePath());
        String directoryPath = FRONT_DIRECTORY_PATH+fileName;
        return directoryPath;
    }

    //解析邮件文件，保存附件至指定文件夹中
    @Override
    public void parseMail(String packageName) {

    }

    //压缩文件夹并返回给前端
    @Override
    public void zipPackage(File filePcg, HttpServletRequest request, HttpServletResponse response) {
        //创建输出流
        OutputStream ops = null;
        ZipOutputStream zops = null;
        try {
            ops = response.getOutputStream();
            response.setHeader("Content-Disposition","attachment;fileName="+
                    URLEncoder.encode(filePcg.getName()+".zip","UTF-8"));
            zops = new ZipOutputStream(ops);
            compress(filePcg,zops,filePcg.getName());//压缩文件夹的方法
            //刷新流
            zops.flush();
            Log.info("文件夹压缩成功，请查收");
            //删除项目中的文件夹
            deleteDir(filePcg);
            Log.info("文件夹:"+filePcg.getName()+"已删除");

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //关闭流
            if (zops != null){
                try {
                    zops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ops != null){
                try {
                    ops.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    //压缩文件夹及其中文件
    private void compress(File sourceFile , ZipOutputStream zops , String name) throws IOException {
        byte[] bytes = new byte[1024];
        if(sourceFile.isFile()){
            //单个文件的压缩，压缩名为文件名
            zops.putNextEntry(new ZipEntry(name));
            int len;
            FileInputStream in = new FileInputStream(sourceFile);
            while((len = in.read(bytes))>0){
                zops.write(bytes,0,len);
            }
            zops.closeEntry();
            in.close();
        }else{
            //文件夹的处理方法
            File[] files = sourceFile.listFiles();
            if (files == null || files.length == 0){
                //文件夹为空的处理方法
                zops.putNextEntry(new ZipEntry(name+"/"));
                zops.closeEntry();
            }else{
                //不为空，递归压缩文件夹下的文件
                for (File file : files) {
                    compress(file,zops,name+"/"+file.getName());
                }
            }
        }



    }

    //删除文件夹
    private void deleteDir(File file) throws IOException {
        FileUtils.deleteDirectory(file);
    }

}
