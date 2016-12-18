package de.bvb;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;

public class MailUtils {
    private static Session session;
    static {
        Properties props = new Properties();
        props.setProperty("mail.smtp.host", "localhost");
        props.setProperty("mail.transport.protocol", "smtp");
        props.setProperty("mail.smtp.auth", "true");
        session = Session.getInstance(props);
    }
    private static MailUtils instance;

    private MailUtils() {
    }

    public static MailUtils getInstance() {
        if (instance == null) {
            instance = new MailUtils();
        }
        return instance;
    }

    public boolean sendMail(Message message) {
        try {
            Transport transport = session.getTransport();
            transport.connect("a", "a");
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
