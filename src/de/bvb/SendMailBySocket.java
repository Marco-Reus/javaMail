package de.bvb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendMailBySocket {

    public static void main(String[] args) throws Exception {

        String send = "<a@bvb.de>";//发件人地址 
        String send_proxy = "<aobama@fbi.com>";//邮件中显示的发件人地址,可以是伪造的
        //        String receiver = "<us0911@126.com>"; //收件人地址
        String receiver = "<b@bvb.de>"; //收件人地址

        // aaa--->bbb
        Socket socket = new Socket("localhost", 25);
        BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        OutputStream out = socket.getOutputStream();

        System.out.println(br.readLine());
        out.write("ehlo hello\r\n".getBytes());

        System.out.println(br.readLine());
        System.out.println(br.readLine());

        out.write("auth login\r\n".getBytes());
        System.out.println(br.readLine());

        out.write("YQ==\r\n".getBytes());
        System.out.println(br.readLine());

        out.write("YQ==\r\n".getBytes());
        System.out.println(br.readLine());

        out.write(("mail from: " + send + "\r\n").getBytes());
        System.out.println(br.readLine());

        out.write(("rcpt to: " + receiver + "\r\n").getBytes());
        System.out.println(br.readLine());

        out.write("data\r\n".getBytes());
        System.out.println(br.readLine());

        out.write(("from:" + send_proxy + "\r\nto:" + receiver + "\r\nsubject:test\r\n\r\n"
                + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date(System.currentTimeMillis())) + "\r\n").getBytes());
        out.write(".\r\n".getBytes());
        System.out.println(br.readLine());

        out.write("quit\r\n".getBytes());
        System.out.println(br.readLine());

        br.close();
        out.close();
        socket.close();
        System.out.println("邮件发送成功");
    }

}
