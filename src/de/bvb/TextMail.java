package de.bvb;

import java.io.UnsupportedEncodingException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * 
 * <p><b>Function:     文本邮件
 * </b></p>Class Name: TextMail<br/>
 * Date:2016-12-18下午3:41:22<br/>author:Administrator<br/>since: JDK 1.6<br/>
 */
public class TextMail {
    public static MimeMessage getMimeMessage(Session session) throws MessagingException, UnsupportedEncodingException {
        //创建邮件
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("a@bvb.de"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("b@bvb.de"));
        message.setSubject("Subject");

        //创建正文
        message.setContent("中文content", "text/html;charset=UTF-8");
        //        message.setContent("content<br/><img src='https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=2906258864,3941883711&fm=96'>","text/html");//将图片以链接的方式放在邮件中
        message.saveChanges();

        return message;

    }

}
