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
import javax.mail.internet.MimeUtility;

/**
 * 
 * <p><b>Function:     附件邮件
 * </b></p>Class Name: AttachmentMail<br/>
 * Date:2016-12-18下午3:49:57<br/>author:Administrator<br/>since: JDK 1.6<br/>
 */
public class AttachmentMail {
    public static MimeMessage getMimeMessage(Session session) throws MessagingException, UnsupportedEncodingException {
        //创建邮件
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("a@de.bvb"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("b@de.bvb"));
        message.setSubject("Subject");

        //创建正文
        MimeBodyPart content = new MimeBodyPart();
        content.setContent("光辉岁月", "text/html;charset=UTF-8");

        //创建附件
        MimeBodyPart attachment = new MimeBodyPart();
        DataHandler handler = new DataHandler(new FileDataSource("src/光辉岁月.MP3"));
        attachment.setDataHandler(handler);
        attachment.setFileName(MimeUtility.encodeText(handler.getName()));

        //描述数据之间的关系
        MimeMultipart mm = new MimeMultipart();
        mm.addBodyPart(content);
        mm.addBodyPart(attachment);
        mm.setSubType("mixed");

        message.setContent(mm);
        message.saveChanges();

        return message;

    }

}
