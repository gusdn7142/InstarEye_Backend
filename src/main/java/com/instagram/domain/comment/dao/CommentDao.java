package com.instagram.domain.comment.dao;


import com.instagram.domain.comment.domain.Comment;
import com.instagram.domain.comment.dto.GetCommentsRes;
import com.instagram.domain.post.domain.Post;
import com.instagram.domain.post.dto.GetPostsRes;
import com.instagram.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;


public interface CommentDao extends JpaRepository<Comment, Long> {


    /* 16. commentIdx 통해 Comment 엔티티 조회  */
    @Query(value="select c from Comment c where c.idx = :commentIdx and c.status = 'ACTIVE'")
    Comment findByIdx(@Param("commentIdx") Long commentIdx);



    /* 15. 전체 댓글 조회 API */
    @Query(value="select c.idx as commentIdx,\n" +
            "       u.idx as commenterIdx,\n" +
            "       u.image as commenterImage,\n" +
            "       u.nick_name as commenterNickName,\n" +
            "       c.content as commentContent,\n" +
            "       case when timestampdiff(minute, c.updated_at, current_timestamp) < 60\n" +
            "           then concat(timestampdiff(minute, c.updated_at, current_timestamp),'분')\n" +
            "\n" +
            "           when timestampdiff(hour, c.updated_at, current_timestamp) < 24\n" +
            "           then concat(timestampdiff(hour, c.updated_at, current_timestamp),'시간')\n" +
            "\n" +
            "           when timestampdiff(day, c.updated_at, current_timestamp) < 30\n" +
            "           then concat(timestampdiff(day, c.updated_at, current_timestamp),'일')\n" +
            "\n" +
            "           else DATE_FORMAT(c.updated_at, '%c월 %e일')\n" +
            "       end commentCreatedTime\n" +
            "\n" +
            "\n" +
            "from comment c join user u\n" +
            "    on c.user_idx = u.idx\n" +
            "join post p\n" +
            "    on c.post_idx = p.idx\n" +
            "\n" +
            "where c.status = 'ACTIVE' and u.status = 'ACTIVE' and p.idx = :postIdx\n" +
            "group by c.idx\n" +
            "order by c.idx DESC", nativeQuery = true)
    List<GetCommentsRes> getComments(Pageable pageable, @Param("postIdx") Long postIdx);




    /* 16. 댓글 작성자가 맞는지 확인  */
    @Query(value="select c from Comment c where c.idx = :commentIdx and c.user = :commenter and c.status = 'ACTIVE' ")
    Comment checkCommentWtriter(@Param("commentIdx") Long commentIdx, @Param("commenter") User commenter);


    /* 16. 댓글 삭제 API */
    @Modifying
    @Transactional
    @Query(value="update Comment c set c.status = 'INACTIVE' where c.idx = :commentIdx and c.status = 'ACTIVE' ")
    void deleteComment(@Param("commentIdx") Long commentIdx);


    /* 17. 댓글 수정 API */
    @Modifying
    @Transactional
    @Query(value="update Comment c set c.content = :content where c.idx = :commentIdx and c.status = 'ACTIVE'")
    void modifyComment(@Param("content") String content, @Param("commentIdx") Long commentIdx);



}
