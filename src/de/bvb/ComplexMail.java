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
 * <p><b>Function:     复杂邮件:包含图片和附件
 * </b></p>Class Name: ComplexMail<br/>
 * Date:2016-12-18下午3:49:12<br/>author:Administrator<br/>since: JDK 1.6<br/>
 */
public class ComplexMail {
    public static MimeMessage getMimeMessage(Session session) throws MessagingException, UnsupportedEncodingException {
        //创建邮件
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("a@de.bvb"));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress("b@de.bvb"));
        message.setSubject("Subject");

        //创建正文
        MimeBodyPart content = new MimeBodyPart();
        content.setContent("正文部分content_start<br/><img src='cid:1.jpg'><br/>content_middle<br/><img src='cid:2.jpg'><br/>content_end",
                "text/html;charset=UTF-8");

        //创建图片
        MimeBodyPart image1 = new MimeBodyPart();
        image1.setContentID("1.jpg"); //这里标签和cid的名字一致,并且不能有中文
        image1.setDataHandler(new DataHandler(new FileDataSource("src/美女1.jpg")));
        //创建图片
        MimeBodyPart image2 = new MimeBodyPart();
        image2.setContentID("2.jpg");
        image2.setDataHandler(new DataHandler(new FileDataSource("src/2.jpg")));

        //创建附件
        MimeBodyPart attachment1 = new MimeBodyPart();
        DataHandler handler1 = new DataHandler(new FileDataSource("src/光辉岁月.mp3"));
        attachment1.setDataHandler(handler1);
        attachment1.setFileName(MimeUtility.encodeText(handler1.getName()));
        //创建附件
        MimeBodyPart attachment2 = new MimeBodyPart();
        DataHandler handler2 = new DataHandler(new FileDataSource("src/美女1.jpg"));
        attachment2.setDataHandler(handler2);
        attachment2.setFileName(MimeUtility.encodeText(handler2.getName()));

        //描述数据之间的关系(正文和图片) 在封装到MimeBodyPart中去
        MimeMultipart contentImageMM = new MimeMultipart();
        contentImageMM.addBodyPart(content);
        contentImageMM.addBodyPart(image1);
        contentImageMM.addBodyPart(image2);
        contentImageMM.setSubType("related");
        MimeBodyPart contentImage = new MimeBodyPart();
        contentImage.setContent(contentImageMM);

        //描述数据之间的关系(把正文和图片封装后的MimeBodyPart,再和附件封装)
        MimeMultipart root = new MimeMultipart();
        root.addBodyPart(contentImage);
        root.addBodyPart(attachment1);
        root.addBodyPart(attachment2);
        contentImageMM.setSubType("mixed");

        message.setContent(root);
        message.saveChanges();

        return message;

    }

}