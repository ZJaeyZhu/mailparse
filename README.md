# mailparse

## MailControl

简易前端页面，主要为上传/下载文件而存在。

## MailService

包含三个方法：

  1.  String createPackage(MultipartFile file);//根据上传文件创建同名文件夹，返回文件夹地址
  2.  void parseMail(String packageName);//解析邮件文件，保存附件至指定文件夹中（暂未完成）
  3.  void zipPackage(File filePcg, HttpServletRequest request, HttpServletResponse response);//生成压缩文件夹并返回给前端
