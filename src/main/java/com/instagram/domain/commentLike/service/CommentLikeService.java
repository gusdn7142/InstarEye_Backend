package com.instagram.domain.commentLike.service;


import com.instagram.domain.comment.dao.CommentDao;
import com.instagram.domain.comment.domain.Comment;
import com.instagram.domain.commentLike.dao.CommentLikeDao;
import com.instagram.domain.commentLike.domain.CommentLike;
import com.instagram.domain.commentLike.dto.PostCommentLikeRes;
import com.instagram.domain.post.dao.PostDao;
import com.instagram.domain.post.domain.Post;
import com.instagram.domain.postLike.domain.PostLike;
import com.instagram.domain.user.dao.UserDao;
import com.instagram.domain.user.domain.User;
import com.instagram.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.instagram.global.error.BasicResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeDao commentLikeDao;
    private final UserDao userDao;
    private final CommentDao commentDao;


    /* 20. 댓글 좋아요  */
    public PostCommentLikeRes createCommentLike(Long userIdx, Long commentIdx) throws BasicException {


        User user = userDao.findByIdx(userIdx);
        Comment comment = commentDao.findByIdx(commentIdx);

        //댓글 삭제 여부 조회
        if(comment == null){             //댓글이 삭제되었다면..
            throw new BasicException(RES_ERROR_COMMENTS_DELETE_COMMENT);    //"삭제된 댓글"
        }

        //댓글 좋아요 중복 조회
        CommentLike commentLikeCheck = commentLikeDao.findByUserAndComment(user, comment);
        if(commentLikeCheck != null){
            throw new BasicException(RES_ERROR_COMMENTLIKES_EXIST_LIKE);    //"댓글 좋아요 중복"
        }


        //DB에 게시글 좋아요 정보 등록 (처음이면)
        try{

            //comment_like DB에 댓글 내용 저장
            CommentLike commentLikeCreation = new CommentLike();
            commentLikeCreation.setUser(user);
            commentLikeCreation.setComment(comment);

            commentLikeDao.save(commentLikeCreation);

            //commentLikeIdx 반환
            CommentLike commentLike = commentLikeDao.findByUserAndComment(user, comment);
            PostCommentLikeRes postCommentLikeRes = new PostCommentLikeRes(commentLike.getIdx());

            return postCommentLikeRes;

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_COMMENTLIKES);  //"DB에 댓글 좋아요 등록 실패"
        }




    }




    /* 21. 댓글 좋아요 취소 -  */
    @Transactional(rollbackFor = {Exception.class})
    public void deleteCommentLike(Long commentLikeIdx, Long userIdx) throws BasicException {

        //댓글 좋아요 삭제 여부 조회 (유저가 계속 클릭시..)
        CommentLike commentLikeDelete = commentLikeDao.findByIdx(commentLikeIdx);
        if(commentLikeDelete == null){
            throw new BasicException(RES_ERROR_COMMENTLIKES_DELETE_LIKE);    //"이미 취소된 댓글 좋아요"
        }

        //댓글 좋아요를 등록자 체크
        User commentLiker = userDao.findByIdx(userIdx);
        if(commentLikeDelete.getUser() != commentLiker) {
            throw new BasicException(RES_ERROR_COMMENTLIKES_NOT_SAME_LIKER);    //댓글 좋아요 등록자 불일치 오류
        }

        try{
            //댓글 좋아요 정보 삭제
            commentLikeDelete.deleteCommentLike();
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_FAIL_DELETE_COMMENTLIKES);   //'DB에서 댓글 좋아요 취소 실패'
        }
    }










}
