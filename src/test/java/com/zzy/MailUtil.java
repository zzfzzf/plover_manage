package com.zzy;
import com.sun.mail.util.MailSSLSocketFactory;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

import static java.awt.SystemColor.text;

public class MailUtil {

    // 发件人的 邮箱 和 密码（替换为自己的邮箱和密码）
    // PS: 某些邮箱服务器为了增加邮箱本身密码的安全性，给 SMTP 客户端设置了独立密码（有的邮箱称为“授权码”）,
    //     对于开启了独立密码的邮箱, 这里的邮箱密码必需使用这个独立密码（授权码）。
    public static String MY_EMAIL = "1203508644@qq.com";
    public static String MY_PASSWORD = "zzfzzf";
    public static String MY_CODE= "uvhdahhmtwpchgid"; //码
    public static String SMTP_HOST = "smtp.qq.com";

    public static void main(String args[]) throws Exception {
        sendEmail("1203508644@qq.com","主题","内容",new Date());
    }
    public static void sendEmail(String receiveMail,String subject,String content,Date createTime) throws Exception {
        Properties props = new Properties();
        // 开启debug调试
        props.setProperty("mail.debug", "true");
        // 发送服务器需要身份验证
        props.setProperty("mail.smtp.auth", "true");
        // 设置邮件服务器主机名
        props.setProperty("mail.host", SMTP_HOST);
        // 发送邮件协议名称
        props.setProperty("mail.transport.protocol", "smtp");
        // 开启ssl验证
        MailSSLSocketFactory sf = new MailSSLSocketFactory();
        sf.setTrustAllHosts(true);
        props.put("mail.smtp.ssl.enable", "true");
        props.put("mail.smtp.ssl.socketFactory", sf);
        // 根据配置创建会话对象, 用于和邮件服务器交互
        Session session = Session.getInstance(props);
        // 1. 创建一封邮件
        MimeMessage message = new MimeMessage(session);
        // 2. From: 发件人
        message.setFrom(new InternetAddress(MY_EMAIL, "龙猫网络", "UTF-8"));
        // 3. To: 收件人（可以增加多个收件人、抄送、密送）
        message.setRecipient(MimeMessage.RecipientType.TO, new InternetAddress(receiveMail,"尊敬的用户", "UTF-8"));
        // 4. Subject: 邮件主题
        message.setSubject(subject, "UTF-8");
        // 5. Content: 邮件正文（可以使用html标签）
        message.setContent(content, "text/html;charset=UTF-8");
        // 6. 设置发件时间
        message.setSentDate(createTime);
        // 7. 保存设置
        message.saveChanges();
        Transport transport = session.getTransport();
        transport.connect(MY_EMAIL, MY_CODE);
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }
}

