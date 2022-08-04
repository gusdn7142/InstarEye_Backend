package com.instagram.domain.comment.service;


import com.instagram.domain.chat.domain.Chat;
import com.instagram.domain.chat.dto.PostChatRes;
import com.instagram.domain.comment.dao.CommentDao;
import com.instagram.domain.comment.domain.Comment;
import com.instagram.domain.comment.dto.GetCommentAndPostRes;
import com.instagram.domain.comment.dto.GetCommentsRes;
import com.instagram.domain.comment.dto.GetPostToCommentRes;
import com.instagram.domain.post.dao.PostDao;
import com.instagram.domain.post.domain.Post;
import com.instagram.domain.post.dto.GetPostsRes;
import com.instagram.domain.user.dao.UserDao;
import com.instagram.domain.user.domain.User;
import com.instagram.domain.user.dto.PatchUserReq;
import com.instagram.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.instagram.global.error.BasicResponseStatus.*;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentDao commentDao;
    private final UserDao userDao;
    private final PostDao postDao;


    /* 14. 댓글 작성  */
    public String createComment(Long userIdx, Long postIdx, String content) throws BasicException {


        //DB에 댓글 내용 등록
        try{
            User user = userDao.findByIdx(userIdx);
            Post post = postDao.findByIdx(postIdx);

            //게시글 삭제 여부 조회 (유저가 계속 클릭시..)
            if(post == null){             //게시글이 삭제되었다면..
                throw new BasicException(RES_ERROR_POSTS_DELETE_POST);    //"삭제된 게시글"
            }

            //comment DB에 댓글 내용 저장
            Comment commentCreation = new Comment();
            commentCreation.setUser(user);
            commentCreation.setPost(post);
            commentCreation.setContent(content);

            commentDao.save(commentCreation);


            return "댓글 등록 성공";

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_COMMENTS);  //DB에 댓글 정보 등록 실패"
        }



    }



    /* 15. 전체 댓글 조회  */
    public GetCommentAndPostRes getComments(Pageable pageable, Long postIdx, Long userIdx ) throws BasicException {

        //게시글 삭제 여부 조회 (유저가 계속 클릭시..)
        Post post = postDao.findByIdx(postIdx);
        if(post == null){             //게시글이 삭제되었다면..
            throw new BasicException(RES_ERROR_POSTS_DELETE_POST);    //"삭제된 게시글"
        }

        try {
            //해당 게시글 내용 조회
            GetPostToCommentRes getPostToCommentRes = postDao.getPostToComment(postIdx);

            //전체 댓글 조회
            List<GetCommentsRes> getCommentsRes = commentDao.getComments(pageable, postIdx, userIdx);

            //Response DTO
            GetCommentAndPostRes getCommentAndPostRes = new GetCommentAndPostRes(getPostToCommentRes,getCommentsRes);

            return getCommentAndPostRes;

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_FAIL_GET_COMMENTS);  //"DB에서 댓글 조회 실패"
        }



    }



    /* 16. 댓글 삭제 -  */
    public void deleteComment(Long commentIdx, Long userIdx) throws BasicException {


        //댓글 삭제 여부 조회 (유저가 계속 클릭시..)
        Comment commentDelete = commentDao.findByIdx(commentIdx);
        if(commentDelete == null){
            throw new BasicException(RES_ERROR_COMMENTS_DELETE_COMMENT);    //"삭제된 댓글"
        }

        //댓글 작성자가 맞는지 확인
        User commenter = userDao.findByIdx(userIdx);
        if(commentDao.checkCommentWtriter(commentIdx, commenter) == null){
            throw new BasicException(RES_ERROR_COMMENTS_NOT_SAME_COMMENTER);    //댓글 작성자 불일치 오류
        }


        try{
            //댓글 정보 삭제
            commentDao.deleteComment(commentIdx);


        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_FAIL_DELETE_COMMENTS);   //'DB에서 댓글 삭제 실패'
        }



    }



    /* 17. 댓글 수정 */
    public void modifyComment(Long commentIdx, Long userIdx, String content) throws BasicException {


        //댓글 작성자가 맞는지 확인
        User commenter = userDao.findByIdx(userIdx);
        if(commentDao.checkCommentWtriter(commentIdx, commenter) == null){
            throw new BasicException(RES_ERROR_COMMENTS_NOT_SAME_COMMENTER);    //댓글 작성자 불일치 오류
        }


        try {
            //댓글 정보 수정
            commentDao.modifyComment(content, commentIdx);
        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_COMMENTS);   //"DB에서 댓글 내용 변경 실패"
        }


    }














    }
