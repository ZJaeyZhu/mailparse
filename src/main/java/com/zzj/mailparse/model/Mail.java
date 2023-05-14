package com.zzj.mailparse.model;

import java.io.Serializable;
import java.util.List;

/**
 * 邮件解析对象
 */
public class Mail implements Serializable {
    //唯一邮件Id
    private String id;
    //发件人
    private String from;
    //收件人
    private String to;
    //邮件主题
    private String subject;
    //内容
    private String content;
    //附件列表
    private List<FileSubject> attachments;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<FileSubject> getAttachments() {
        return attachments;
    }

    public void setAttachments(List<FileSubject> attachments) {
        this.attachments = attachments;
    }
}
