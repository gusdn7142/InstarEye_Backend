package com.instagram.global.error;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.instagram.test.GetTestReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.instagram.global.error.BasicResponseStatus.SUCCESS;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"status", "code", "message", "result"})
public class BasicResponse {

    @ApiModelProperty(notes = "응답상태", example = "SUCCESS")
    private String status;

    @ApiModelProperty(notes = "에러코드",example = "NOT_ERROR")
    private String code;

    @ApiModelProperty(notes = "응답 메시지" , example = "요청 성공")
    private String message;

    @ApiModelProperty(notes = "응답 결과" ,example = "API마다 응답해주는 자원이 다릅니다.")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object result;



    /* 요청에 성공한 경우 */
    public BasicResponse(Object result) {
        this.status = SUCCESS.getStatus();
        this.code = SUCCESS.getCode();
        this.message = SUCCESS.getMessage();

        this.result = result;
    }


    /* 요청에 실패한 경우 */
    public BasicResponse(BasicResponseStatus status) {
        this.status = status.getStatus();
        this.code = status.getCode();
        this.message = status.getMessage();
    }


}
