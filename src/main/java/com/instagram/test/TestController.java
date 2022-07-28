package com.instagram.test;

import com.instagram.global.config.BasicResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.instagram.global.config.BasicResponseStatus.*;


@Api(tags = "테스트 API")
@Slf4j
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


    /* 스웨거 테스트 API */
    @ResponseBody
    @GetMapping("/swegger/{id}")
    @ApiOperation(value = "스웨거 테스트 API", notes = "스웨거 테스트 API입니다" )
    public String testSwegger(@ApiParam(value = "유저 인덱스")
                              @PathVariable int id) {


        System.out.println("스웨거 테스트 성공 (콘솔), id는" + id);



        return "스웨거 테스트 성공";
    }


    /**
     * API Response 테스트
     */
    @ResponseBody
    @GetMapping("/response/{id}")
    @ApiOperation(value = "API Response Test", notes = "API Response Test 입니다." )
    public BasicResponse testResponse(@ApiParam(value = "유저 인덱스") @PathVariable int id) {


        if(id == 1){
            return new BasicResponse("요청 성공");
        }
        else
            return new BasicResponse(DATABASE_ERROR);

    }











}