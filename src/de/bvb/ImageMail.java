package de.bvb;

import java.io.UnsupportedEncodingException;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * 
 * <p><b>Function:     包含图片的邮件
 * </b></p>Class Name: ImageMail<br/>
 * Date:2016-12-18下午3:39:48<br/>author:Administrator<br/>since: JDK 1.6<br/>
 */
public class ImageMail {
    public MimeMessage getMimeMessage(Session session) throws MessagingException, UnsupportedEncodingException {
        //创建邮件
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("a@de.bvb"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("b@de.bvb"));
        message.setSubject("Subject");

        //创建正文
        MimeBodyPart content = new MimeBodyPart();
        content.setContent("中文content_start<br/><img src='cid:1.jpg'><br/>content_end", "text/html;charset=UTF-8");

        //创建图片
        MimeBodyPart image = new MimeBodyPart();
        image.setContentID("1.jpg");
        image.setDataHandler(new DataHandler(new FileDataSource("src/美女1.jpg")));

        //描述数据之间的关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(content);
        mm.addBodyPart(image);
        mm.setSubType("related");

        message.setContent(mm);
        message.saveChanges();

        return message;

    }

}