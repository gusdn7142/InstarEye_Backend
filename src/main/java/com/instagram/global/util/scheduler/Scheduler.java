package com.instagram.global.util.scheduler;


import com.instagram.domain.user.dao.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor

public class Scheduler {

    private final UserDao userDao;


//    //3초마다 실행되는 스케줄러
//    @Scheduled(fixedDelay=3000)
//    public void schedulerTest() {
//        //log.info("3초마다 스케줄러 실행");
//        userDao.modifyPrivacyPolicyStatus();
//    }


    //매일 자정 실행되는 스케줄러
    @Scheduled(cron = "0 0 0 * * *")   //초,분,시,일,월,요일(생략가능)
    public void checkPrivacyPolicy() {
        log.info("매일 자정 실행됨");
        userDao.modifyPrivacyPolicyStatus();
    }



}
