package de.bvb.utils;

import java.util.List;

public class MailInfo {
    private String to;
    private String subject;
    private String content;
    private List<String> imageUrlList;
    private List<String> attachmentUrlList;

    public MailInfo() {
        super();
    }

    public MailInfo(String to, String subject, String content) {
        super();
        this.to = to;
        this.subject = subject;
        this.content = content;
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

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public List<String> getAttachmentUrlList() {
        return attachmentUrlList;
    }

    public void setAttachmentUrlList(List<String> attachmentUrlList) {
        this.attachmentUrlList = attachmentUrlList;
    }

}
