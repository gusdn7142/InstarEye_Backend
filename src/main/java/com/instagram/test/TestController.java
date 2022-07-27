package com.instagram.test;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;



@Slf4j   //final Logger log = LoggerFactory.getLogger(this.getClass());  // SLF4J 로거 등록
@RestController
@RequestMapping("/test")
public class TestController {


    @Autowired
    public TestController() {}

    /* 테스트 API */
    @ResponseBody
    @GetMapping("/log")
    public String getTest() {
        System.out.println("API 테스트 성공 (콘솔)");

        log.info("API 테스트 성공 ");
        log.warn("API 테스트 성공 (경고) ");
        log.error("API 테스트 성공 (에러)");

        return "API 테스트 성공 (웹 페이지)";
    }


}