package de.bvb.utils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.List;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class MailUtils {
    ///////////////////////////////////////////////////////////////
    //单例模式//////////////////////////////////////////////////////
    private static MailUtils instance;

    private MailUtils() {
    }

    public static MailUtils getInstance() {
        if (instance == null) {
            instance = new MailUtils();
        }
        return instance;
    }

    //单例模式//////////////////////////////////////////////////////
    ///////////////////////////////////////////////////////////////

    private static Session session;
    private static String emailAddress;
    private static String username;
    private static String password;

    // 配置文件路径: src/mailConfig.properties
    static {
        try {
            Properties props = new Properties();
            props.setProperty("mail.transport.protocol", "smtp");
            props.setProperty("mail.smtp.auth", "true");
            props.load(MailUtils.class.getClassLoader().getResourceAsStream("mailConfig.properties"));
            emailAddress = props.getProperty("emailAddress");
            username = emailAddress.split("\\@")[0];
            password = props.getProperty("password");
            session = Session.getInstance(props);
            //session.setDebug(true);//启动调试,控制台打印交互信息
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean sendMail(MailInfo info) {
        if (info == null) {
            throw new RuntimeException("信息不全");
        }
        try {
            Message message = createMail(info);
            Transport transport = session.getTransport();
            transport.connect(username, password);
            transport.sendMessage(message,  message.getAllRecipients());
            transport.close();
            System.out.println("发送成功");
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Message createMail(MailInfo info) throws MessagingException, UnsupportedEncodingException {
        if (info == null) {
            return null;
        }
        //创建邮件
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(emailAddress));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(info.getTo()));
        message.setSubject(info.getSubject());

        MimeMultipart root = new MimeMultipart();
        MimeMultipart contentImageMM = new MimeMultipart();
        StringBuilder main = new StringBuilder(info.getContent());

        //创建图片
        List<String> imageUrlList = info.getImageUrlList();
        if (imageUrlList != null && imageUrlList.size() > 0) {
            for (String imageUrl : imageUrlList) {
                String cid = generatorMD5(imageUrl); //id需要唯一,md5处理一把
                MimeBodyPart image1 = new MimeBodyPart();
                image1.setContentID(cid);
                image1.setDataHandler(new DataHandler(new FileDataSource(imageUrl)));
                main.append("<br/><img src='cid:").append(cid).append("'>");
                contentImageMM.addBodyPart(image1);
            }
        }

        //描述数据之间的关系(正文和图片) 在封装到MimeBodyPart中去
        MimeBodyPart content = new MimeBodyPart();
        content.setContent(main.toString(), "text/html;charset=UTF-8");
        contentImageMM.addBodyPart(content);
        contentImageMM.setSubType("related");
        MimeBodyPart contentImage = new MimeBodyPart();
        contentImage.setContent(contentImageMM);

        //创建附件
        List<String> attachmentUrlList = info.getAttachmentUrlList();
        if (attachmentUrlList != null && attachmentUrlList.size() > 0) {
            for (String attachmentUrl : attachmentUrlList) {
                MimeBodyPart attachment1 = new MimeBodyPart();
                DataHandler handler1 = new DataHandler(new FileDataSource(attachmentUrl));
                attachment1.setDataHandler(handler1);
                attachment1.setFileName(MimeUtility.encodeText(handler1.getName()));
                root.addBodyPart(attachment1);
            }
        }

        //描述数据之间的关系(把正文和图片封装后的MimeBodyPart,再和附件封装)
        root.addBodyPart(contentImage);
        contentImageMM.setSubType("mixed");

        //封装保存数据
        message.setContent(root);
        message.saveChanges();
        return message;
    }

    private String generatorMD5(String pwd) {
        //用于加密的字符  
        char md5String[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            //使用平台的默认字符集将此 String 编码为 byte序列，并将结果存储到一个新的 byte数组中  
            byte[] btInput = pwd.getBytes();
            //信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。  
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            //MessageDigest对象通过使用 update方法处理数据， 使用指定的byte数组更新摘要  
            mdInst.update(btInput);
            // 摘要更新之后，通过调用digest（）执行哈希计算，获得密文  
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式  
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) { //  i = 0  
                byte byte0 = md[i]; //95  
                str[k++] = md5String[byte0 >>> 4 & 0xf]; //    5   
                str[k++] = md5String[byte0 & 0xf]; //   F  
            }
            //返回经过加密后的字符串  
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}
