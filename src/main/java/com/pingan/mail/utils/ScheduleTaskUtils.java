package com.pingan.mail.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class ScheduleTaskUtils {

    public final static Logger log = LoggerFactory.getLogger(ScheduleTaskUtils.class);

    //添加任务、设定时间
    @Scheduled(cron = "*/5 * * * * ?")
    public static void scheduledwork(){
        log.info("xxxxxxxxxxxxxxxxxxxxxx");
    }

}
