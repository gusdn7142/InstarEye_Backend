package com.instagram.domain.user.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {


    private Long idx;
    private String name;
    private String image;
    private String nickName;

    private String webSite;
    private String Introduction;

}
