package com.instagram.domain.postLike.service;


import com.instagram.domain.post.dao.PostDao;
import com.instagram.domain.post.domain.Post;
import com.instagram.domain.postLike.dao.PostLikeDao;
import com.instagram.domain.postLike.domain.PostLike;
import com.instagram.domain.postLike.dto.PostPostLikeRes;
import com.instagram.domain.user.dao.UserDao;
import com.instagram.domain.user.domain.User;
import com.instagram.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.instagram.global.error.BasicResponseStatus.*;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeDao postLikeDao;
    private final UserDao userDao;
    private final PostDao postDao;


    /* 18. 게시글 좋아요  */
    public PostPostLikeRes createPostLike(Long userIdx, Long postIdx) throws BasicException {


        User user = userDao.findByIdx(userIdx);
        Post post = postDao.findByIdx(postIdx);

        //게시글 삭제 여부 조회
        if(post == null){             //게시글이 삭제되었다면..
            throw new BasicException(RES_ERROR_POSTS_DELETE_POST);    //"삭제된 게시글"
        }

        //게시글 좋아요 중복 조회
        PostLike postLikeCheck = postLikeDao.findByUserAndPost(user, post);
        if(postLikeCheck != null){
            throw new BasicException(RES_ERROR_POSTLIKES_EXIST_LIKE);    //"게시물 좋아요 중복"
        }


        //DB에 게시글 좋아요 정보 등록 (처음이면)
        try{

            //post_like DB에 댓글 내용 저장
            PostLike postLikeCreation = new PostLike();
            postLikeCreation.setUser(user);
            postLikeCreation.setPost(post);

            postLikeDao.save(postLikeCreation);

            //postLikeIdx 반환
            PostLike postLike = postLikeDao.findByUserAndPost(user, post);
            PostPostLikeRes postPostLikeRes = new PostPostLikeRes(postLike.getIdx());

            return postPostLikeRes;

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_POSTLIKES);  //"DB에 게시글 좋아요 등록 실패"
        }




    }



    /* 19. 게시글 좋아요 취소 -  */
    public void deletePostLike(Long postLikeIdx, Long userIdx) throws BasicException {


        //게시글 좋아요 삭제 여부 조회 (유저가 계속 클릭시..)
        PostLike postLikeDelete = postLikeDao.findByIdx(postLikeIdx);
        if(postLikeDelete == null){
            throw new BasicException(RES_ERROR_POSTLIKES_DELETE_LIKE);    //"이미 취소된 게시글 좋아요"
        }

        //게시글 좋아요를 등록자 체크
        User postLiker = userDao.findByIdx(userIdx);
        if(postLikeDao.checkPostLiker(postLikeIdx, postLiker) == null){
            throw new BasicException(RES_ERROR_POSTLIKES_NOT_SAME_LIKER);    //게시물 좋아요 등록자 불일치 오류
        }


        try{
            //게시글 좋아요 정보 삭제
            postLikeDao.deletePostLike(postLikeIdx);


        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_FAIL_DELETE_POSTLIKES);   //'DB에서 게시글 좋아요 취소 실패'
        }



    }




}
