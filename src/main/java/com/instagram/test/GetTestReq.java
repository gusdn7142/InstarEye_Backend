package com.instagram.test;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetTestReq {

    @ApiModelProperty(notes= "사용자 인덱스", example = "1")
    int userIdx;

    @ApiModelProperty(notes="사용자 이름", example = "뎁스")
    String name;

    @ApiModelProperty(notes= "사용자 이메일", example = "depth.email")
    String email;






}
