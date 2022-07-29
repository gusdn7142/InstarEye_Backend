package com.instagram.test;

import com.instagram.global.error.BasicResponse;


import io.swagger.annotations.*;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static com.instagram.global.error.BasicResponseStatus.*;




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
    @PostMapping("/response/{id}")
    @ApiOperation(value = "API Response Test", notes = "API Response Test 입니다." )
    @ApiResponses(value = {
            //@ApiResponse(code  = 200, message = "저장 성공"),
            @ApiResponse(code = 201,  message = "1. 'status' : 'FAIL', 'code' : 'DATABASE_ERROR', 'message' = 'DB에서 데이터 조회 실패' \t\n" +
                                                "2. 'status' : 'FAIL', 'code' : 'SERVER_ERROR', 'message' = '서버에서 오류 발생'", response = BasicResponse.class),
    })
    public BasicResponse testResponse(@ApiParam(name = "id", value = "유저 인덱스", example = "1") @PathVariable int id,
                                      @RequestBody GetTestReq getTestReq) {


        if(id == 1){
            return new BasicResponse(getTestReq);
        }
        else
            return new BasicResponse(DATABASE_ERROR);


   }


}