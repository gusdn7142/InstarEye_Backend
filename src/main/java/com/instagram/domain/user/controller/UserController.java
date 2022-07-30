package com.instagram.domain.user.controller;


import com.instagram.domain.user.domain.PrivacyPolicyStatus;
import com.instagram.domain.user.dto.PostLoginReq;
import com.instagram.domain.user.dto.PostLoginRes;
import com.instagram.domain.user.dto.PostUserReq;
import com.instagram.domain.user.service.UserService;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.logging.LogLevel;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.instagram.global.error.BasicResponseStatus.*;

@Api(tags = "사용자(user) API")
@RestController
@RequestMapping("users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;




    /*
     * 1. 회원가입 API
     * [POST] /users
     * @return BaseResponse(responseMessage)
     */
    @ApiOperation(value = "회원가입 API", notes = "URL : https://in-stagram.site/users" )
    @ApiResponses(value = {
            @ApiResponse(code = 201,  message = "1. 'status' : 'FAIL', 'code' : 'POST_USERS_INVALID_PHONE', 'message' = '전화번호 형식 오류' \t\n" +
                                                "2. 'status' : 'FAIL', 'code' : 'POST_USERS_INVALID_NAME', 'message' = '이름 형식 오류' \t\n" +
                                                "3. 'status' : 'FAIL', 'code' : 'POST_USERS_INVALID_PASSWORD', 'message' = '비밀번호 형식 오류' \t\n" +
                                                "4. 'status' : 'FAIL', 'code' : 'POST_USERS_INVALID_BIRTHDAY', 'message' = '생일 형식 오류' \t\n" +
                                                "4. 'status' : 'FAIL', 'code' : 'POST_USERS_INVALID_PRIVACY_POLICY_STATUS', 'message' = '개인정보 처리방침 미동의' \t\n" +
                                                "4. 'status' : 'FAIL', 'code' : 'POST_USERS_INVALID_NICK_NAME', 'message' = '닉네임 형식 오류' \t\n" +
                                                "5. 'status' : 'FAIL', 'code' : 'POST_USERS_EXISTS_PHONE', 'message' = '전화번호 중복 오류' \t\n" +
                                                "6. 'status' : 'FAIL', 'code' : 'POST_USERS_EXISTS_NICK_NAME', 'message' = '닉네임 중복 오류' \t\n" +
                                                "7. 'status' : 'FAIL', 'code' : 'DATABASE_ERROR_CREATE_USER', 'message' = 'DB에 사용자 등록 실패' \t\n"
                                                , response = BasicResponse.class),
    })
    @PostMapping("")
    public BasicResponse createUser(@Valid @RequestBody PostUserReq postUserReq, BindingResult bindingResult) {


        /* 유효성 검사 구현부 */
        if(bindingResult.hasErrors()) {   //에러가 있다면
            List<ObjectError> errorlist = bindingResult.getAllErrors();
            String errorCode = errorlist.get(0).getCodes()[0];

            if (errorCode.equals("Pattern.postUserReq.phone")) {
                return new BasicResponse(REQ_ERROR_INVALID_PHONE);
            }
            else if (errorCode.equals("Size.postUserReq.name")) {
                return new BasicResponse(REQ_ERROR_INVALID_NAME);
            }
            else if (errorCode.equals("Size.postUserReq.password")) {
                return new BasicResponse(REQ_ERROR_INVALID_PASSWORD);
            }
            else if (errorCode.equals("Pattern.postUserReq.birthDay")) {
                return new BasicResponse(REQ_ERROR_INVALID_BIRTHDAY);
            }
            else if (errorCode.equals("Pattern.postUserReq.privacyPolicyStatus")) {
                return new BasicResponse(REQ_ERROR_INVALID_PRIVACY_POLICY_STATUS);
            }
            else if (errorCode.equals("Size.postUserReq.nickName")) {
                return new BasicResponse(REQ_ERROR_INVALID_NICK_NAME);
            }
        }
        /* 유효성 검사 구현 끝 */


        try{

            //DB에 유저 등록
            String responseMessage = userService.createUser(postUserReq);

            return new BasicResponse(responseMessage);
        } catch(BasicException exception){
            return new BasicResponse(exception.getStatus());
        }



    }





    /*
     * 2. 로그인 API
     * [POST] /users/login
     * @return BaseResponse(PostLoginRes)
     */
    @PostMapping("/login")
    public BasicResponse login(@Valid @RequestBody PostLoginReq postLoginReq, BindingResult bindingResult) {


        /* 유효성 검사 구현부 */
        if(bindingResult.hasErrors()) {   //에러가 있다면
            List<ObjectError> errorlist = bindingResult.getAllErrors();
            String errorCode = errorlist.get(0).getCodes()[0];

            if (errorCode.equals("Pattern.postLoginReq.phone")) {
                return new BasicResponse(REQ_ERROR_INVALID_PHONE);
            }
            else if (errorCode.equals("Size.postLoginReq.password")) {
                return new BasicResponse(REQ_ERROR_INVALID_PASSWORD);
            }
        }
        /* 유효성 검사 구현 끝 */




        try{
            //로그인
            PostLoginRes postLoginRes = userService.login(postLoginReq);


            return new BasicResponse(postLoginRes);
        } catch(BasicException exception){
            return new BasicResponse(exception.getStatus());
        }


    }

















}
