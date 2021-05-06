package com.pingan.mail.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@Component
public class MailServiceUtils {
    //默认编码
    public static final String DEFAULT_ENCODING = "UTF-8";
    private static JavaMailSender mailSender;

    private final static Logger log = LoggerFactory.getLogger(MailServiceUtils.class);

    private MailServiceUtils(JavaMailSender mailSender){
        MailServiceUtils.mailSender = mailSender;
    }


    public static void sendSimpleTextMail(String userName, String nickName,
                                          String subject, String content,
                                          String[] toWho, String[] ccPeoples,
                                          String[] bccPeoples, String[] attachments){
        //检验参数：邮件主题、收件人、邮件内容必须不为空才能够保证基本的逻辑执行
        if(subject == null||toWho == null||toWho.length == 0||content == null){
            log.error("邮件-> {} 无法继续执行，因为缺少基本的参数：邮件主题、收件人、邮件内容",subject);
            throw new RuntimeException("模板邮件无法继续发送，因为缺少必要的参数！");
        }
        log.info("开始发送简单文本邮件：主题->{}，收件人->{}，抄送人->{}，密送人->{}，附件->{}",subject,toWho,ccPeoples,bccPeoples,attachments);

        if(attachments != null && attachments.length > 0){
            //附件处理，需要处理附件时，需要使用二进制信息，使用 MimeMessage 类来进行处理
            try{
                //附件处理需要进行二进制传输
                MimeMessage mimeMessage = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,DEFAULT_ENCODING);
                //设置邮件的基本信息：这些函数都会在后面列出来
                handleMimeMessageInfo(helper, userName, nickName, subject, content, toWho, ccPeoples, bccPeoples,false);
                //处理附件
                handleAttachment(helper,subject,attachments);
                //发送该邮件
                mailSender.send(mimeMessage);
                log.info("发送邮件成功: 主题->{}",subject);
            } catch (MessagingException e) {
                log.error("发送邮件失败: 主题->{}",subject);
            }
        }else{
            try {
                //创建一个简单邮件信息对象
                SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
                //设置邮件的基本信息
                handleSimpleMailInfo(simpleMailMessage, userName, nickName, subject, content, toWho, ccPeoples, bccPeoples);
                //发送邮件
                mailSender.send(simpleMailMessage);
                log.info("发送文本邮件成功: 主题->{}",subject);
            }catch (MailException e){
                log.error("发送文本邮件失败: 主题->{}",subject);
            }
        }
    }

    public static void sendHtmlMail(String userName, String nickName, String subject,String content,String[] toWho,String[] ccPeoples,String[] bccPeoples) {

        //检验参数：邮件主题、收件人、邮件内容必须不为空才能够保证基本的逻辑执行
        if(subject == null||toWho == null||toWho.length == 0||content == null){
            log.error("邮件-> {} 无法继续执行，因为缺少基本的参数：邮件主题、收件人、邮件内容",subject);
            throw new RuntimeException("模板邮件无法继续发送，因为缺少必要的参数！");
        }
        log.info("开始发送Html邮件：主题->{}，收件人->{}",subject,toWho);
        //html
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true,DEFAULT_ENCODING);
            //设置邮件的基本信息
            handleMimeMessageInfo(helper, userName, nickName, subject, content, toWho, ccPeoples, bccPeoples,true);
            //发送邮件
            mailSender.send(mimeMessage);
            log.info("发送Html邮件成功: 主题->{}",subject);
        } catch (MessagingException e) {
            log.error("发送Html邮件出错: 主题->{}",subject);
        }
    }

    private static void handleMimeMessageInfo(MimeMessageHelper mimeMessageHelper,
                                              String userName, String nickName,
                                              String subject, String content, String[] toWho,
                                              String[] ccPeoples, String[] bccPeoples, boolean isHtml){
        try{
            //设置必要的邮件元素
            //设置发件人
            mimeMessageHelper.setFrom(nickName+'<'+userName+'>');
            //设置邮件的主题
            mimeMessageHelper.setSubject(subject);
            //设置邮件的内容，区别是否是HTML邮件
            mimeMessageHelper.setText(content,isHtml);
            //设置邮件的收件人
            mimeMessageHelper.setTo(toWho);
            //设置非必要的邮件元素，在使用helper进行封装时，这些数据都不能够为空
            if(ccPeoples != null)
                //设置邮件的抄送人：MimeMessageHelper # Assert.notNull(cc, "Cc address array must not be null");
                mimeMessageHelper.setCc(ccPeoples);
            if(bccPeoples != null)
                //设置邮件的密送人：MimeMessageHelper # Assert.notNull(bcc, "Bcc address array must not be null");
                mimeMessageHelper.setBcc(bccPeoples);
        }catch(MessagingException e){
            e.printStackTrace();
            log.error("邮件二进制信息出错->{}",subject);
        }
    }

    private static void handleSimpleMailInfo(SimpleMailMessage simpleMailMessage,
                                             String userName, String nickName,
                                             String subject, String content, String[] toWho,
                                             String[] ccPeoples, String[] bccPeoples){
        //设置发件人
        simpleMailMessage.setFrom(nickName+'<'+userName+'>');
        //设置邮件的主题
        simpleMailMessage.setSubject(subject);
        //设置邮件的内容
        simpleMailMessage.setText(content);
        //设置邮件的收件人
        simpleMailMessage.setTo(toWho);
        //设置邮件的抄送人
        simpleMailMessage.setCc(ccPeoples);
        //设置邮件的密送人
        simpleMailMessage.setBcc(bccPeoples);
    }

    private static void handleAttachment(MimeMessageHelper mimeMessageHelper,String subject,String[] attachmentFilePaths){
        //判断是否需要处理邮件的附件
        if(attachmentFilePaths != null && attachmentFilePaths.length > 0) {
            FileSystemResource resource;
            String fileName;
            //循环处理邮件的附件
            for (String attachmentFilePath : attachmentFilePaths) {
                //获取该路径所对应的文件资源对象
                resource = new FileSystemResource(new File(attachmentFilePath));
                // 判断该资源是否存在，当不存在时仅仅会打印一条警告日志，不会中断处理程序。
                // 也就是说在附件出现异常的情况下，邮件是可以正常发送的，所以请确定你发送的邮件附件在本机存在
                if (!resource.exists()) {
                    log.warn("邮件->{} 的附件->{} 不存在！", subject, attachmentFilePath);
                    //开启下一个资源的处理
                    continue;
                }
                //获取资源的名称
                fileName = resource.getFilename();
                try {
                    //添加附件
                    mimeMessageHelper.addAttachment(fileName, resource);
                } catch (MessagingException e) {
                    e.printStackTrace();
                    log.error("邮件->{} 添加附件->{} 出现异常->{}", subject, attachmentFilePath, e.getMessage());
                }
            }
        }
    }
}
