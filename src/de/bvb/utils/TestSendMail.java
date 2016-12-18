package de.bvb.utils;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestSendMail {

    List<String> imageUrlList = Arrays.asList(new String[] { "src/美女1.jpg", "src/2.jpg", "src/2.jpg" });
    List<String> attachmentUrlList = Arrays.asList(new String[] { "src/光辉岁月.MP3", "src/美女1.jpg", "src/2.jpg" });

    @Test
    public void test1() {
        List<String> imageUrlList = Arrays.asList(new String[] { "src/美女1.jpg", "src/2.jpg", "src/2.jpg" });
        List<String> attachmentUrlList = Arrays.asList(new String[] { "src/光辉岁月.MP3", "src/美女1.jpg", "src/2.jpg" });

        MailInfo mailInfo = new MailInfo("us0911@126.com", "subject", "content");
        mailInfo.setImageUrlList(imageUrlList);
        MailUtils.getInstance().sendMail(mailInfo);
    }

}
