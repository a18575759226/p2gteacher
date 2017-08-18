package com.xmg.p2p.base.service.impl;

import com.xmg.p2p.base.domain.MailVerify;
import com.xmg.p2p.base.service.IEmailService;
import com.xmg.p2p.base.service.IMailVerifyService;
import com.xmg.p2p.base.util.BidConst;
import com.xmg.p2p.base.util.DateUtil;
import com.xmg.p2p.base.util.UserContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;

/**
 * Created by lanxw on 2017/7/26.
 */
@Service
public class EmailServiceImpl implements IEmailService {
    @Value("${email.applicationUrl}")
    private String applicationUrl;
    @Value("${email.fromEmail}")
    private String fromEmail;
    @Value("${email.username}")
    private String username;
    @Value("${email.password}")
    private String password;
    @Value("${email.hosts}")
    private String hosts;
    @Autowired
    private IMailVerifyService mailVerifyService;
    @Override
    public void sendEmail(String email) {
        //1.生成UUID的字符串
        String uuid = UUID.randomUUID().toString();
        //2.发送邮件
        StringBuilder stringBuilder = new StringBuilder(100);
        stringBuilder.append("感谢您注册P2P网站的账号,请点击这个链接进行邮箱的绑定:<a href=\"")
                .append(applicationUrl)
                .append("/bindEmail.do?key=")
                .append(uuid)
                .append("\">立即绑定</a>")
                .append(",有效时间为:").append(BidConst.EMAIL_VAILD_DAY).append("天");
        //真实发送的邮件
        try{
        	System.out.println(stringBuilder);
            //sendRealEmail(email,stringBuilder.toString());
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        //3.把当前需要绑定的邮箱,当前用户的UserId,发送的时间保存数据库中.
        MailVerify mailVerify = new MailVerify();
        mailVerify.setUuid(uuid);
        mailVerify.setEmail(email);
        mailVerify.setSendTime(new Date());
        mailVerify.setUserId(UserContext.getCurrent().getId());
        mailVerifyService.insert(mailVerify);
    }
    private void sendRealEmail(String toEmaill,String content) throws Exception {
        //创建发送邮件的实现类对象
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        //设置邮件服务器
        sender.setHost(this.hosts);
        //封装邮件信息
        MimeMessage mimeMessage = sender.createMimeMessage();
        //定义工具类
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,"utf-8");
        //设置收件,发件人信息.
        helper.setTo(toEmaill);
        helper.setFrom(this.fromEmail);
        helper.setSubject("这是一封认证邮件!");
        helper.setText(content,true);
        sender.setUsername(this.username);
        sender.setPassword(this.password);
        //封装发送的信息
        Properties prop = new Properties();
        prop.setProperty("mail.smtp.auth","true");
        prop.setProperty("mail.smtp.timeout","25000");
        sender.setJavaMailProperties(prop);
        //发送邮件信息
        sender.send(mimeMessage);
    }
    @Override
    public MailVerify validate(String key) {
        MailVerify mailVerify = mailVerifyService.selectByUUID(key);
        if(mailVerify==null || DateUtil.getSecondsBetween(mailVerify.getSendTime(),new Date())>BidConst.EMAIL_VAILD_DAY*24*60*60){
            return null;
        }
        return mailVerify;
    }
}
