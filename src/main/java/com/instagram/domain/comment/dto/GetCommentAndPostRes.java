package com.instagram.domain.comment.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetCommentAndPostRes {

    private GetPostToCommentRes getPostToCommentRes;
    private List<GetCommentsRes> getCommentsRes;

}
