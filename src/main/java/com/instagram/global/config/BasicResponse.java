package com.instagram.global.config;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import static com.instagram.global.config.BasicResponseStatus.SUCCESS;


@Getter
@AllArgsConstructor
@JsonPropertyOrder({"status", "code", "message", "result"})
public class BasicResponse {

    private String status;
    private String code;
    private String message;

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
