package com.zzj.mailparse.model;


/**
 * @author ZJaey
 * @version v1.0.0
 * @Package : com.zzj.mailparse.model
 * @Description : 所有异常信息的枚举类
 * @Create on : 2023/5/13 17:35
 **/
public enum RespEnum {
    //文件格式异常
    FILE_ERROR(502,"文件格式不对，需要EML文件"),
    //通用
    SUCCESS(200,"SUCCESS"),
    ERROR(500,"服务端异常"),
    ;


    private Integer code;
    private String message;

    private RespEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
