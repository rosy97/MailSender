package com.pingan.mail.controller;

import com.pingan.mail.dto.Response;
import com.pingan.mail.exception.MailServiceException;
import com.pingan.mail.utils.MailServiceUtils;
import com.pingan.mail.utils.ScheduleTaskUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mail")
public class MailController {

    private final static Logger log = LoggerFactory.getLogger(MailController.class);

    // 邮件的发送者，来自邮件配置
    @Value("${spring.mail.username}")
    public String userName;
    @Value("${spring.mail.nickname}")
    public String nickName;


    @RequestMapping(value = "/simple", method = RequestMethod.POST)
    public Response simpleSend(@RequestParam String subject, //主题
                               @RequestParam String content, //内容
                               @RequestParam String[] receivers, //接收者
                               String[] cc, String[] bcc, String[] attaches) {
        log.info("simple sender.....");
        log.info("userName->{} ---------- nickName->{}", userName,nickName);

//        subject = "test mail";
//        receivers = new String[]{"***@163.com"};
//        cc = new String[]{}; //抄送人
//        bcc = new String[]{}; //暗送人
//        attaches = new String[]{"filePath"};
//        String[] emptyAttch = new String[]{};

        // 对接收者邮件格式进行校验
        for(String receiver : receivers){
            if(!receiver.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")) {
                log.error("邮箱格式错误 -->{}",receiver);
                throw new MailServiceException();
            }
        }
        for(String ccReceiver : cc){
            if(!ccReceiver.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")) {
                log.error("邮箱格式错误 -->{}",ccReceiver);
                throw new MailServiceException();
            }
        }
        for(String bccReceiver : bcc){
            if(!bccReceiver.matches("^\\s*\\w+(?:\\.{0,1}[\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\.[a-zA-Z]+\\s*$")) {
                log.error("邮箱格式错误 -->{}",bccReceiver);
                throw new MailServiceException();
            }
        }

        MailServiceUtils.sendSimpleTextMail(userName, nickName, subject, content, receivers, cc, bcc, attaches);
        return Response.ok();
    }


}
