package com.instagram.domain.post.dao;

import com.instagram.domain.comment.dto.GetCommentsRes;
import com.instagram.domain.comment.dto.GetPostToCommentRes;
import com.instagram.domain.post.domain.Post;
import com.instagram.domain.post.dto.GetPostRes;
import com.instagram.domain.post.dto.GetPostsRes;
import com.instagram.domain.user.domain.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

public interface PostDao extends JpaRepository<Post, Long> {

    /* 7. postIdx 통해 Post 엔티티 조회  */
    @Query(value="select p from Post p where p.idx = :postIdx and p.status = 'ACTIVE'")
    Post findByIdx(@Param("postIdx") Long postIdx);


    /* 6. 전체 게시글 조회 API */
    @Query(value="select u.idx writerIdx,\n" +
            "       u.nick_name writerNickName,\n" +
            "       u.image writerImage,\n" +
            "       p.idx postIdx,\n" +
            "       p.content postContent,\n" +
            "       case when timestampdiff(second , p.updated_at, current_timestamp) <60\n" +
            "           then concat(timestampdiff(second, p.updated_at, current_timestamp),'초 전')\n" +
            "\n" +
            "           when timestampdiff(minute , p.updated_at, current_timestamp) <60\n" +
            "           then concat(timestampdiff(minute, p.updated_at, current_timestamp),'분 전')\n" +
            "\n" +
            "           when timestampdiff(hour , p.updated_at, current_timestamp) <24\n" +
            "           then concat(timestampdiff(hour, p.updated_at, current_timestamp),'시간 전')\n" +
            "\n" +
            "           when timestampdiff(day , p.updated_at, current_timestamp) < 30\n" +
            "           then concat(timestampdiff(day, p.updated_at, current_timestamp),'일 전')\n" +
            "\n" +
            "           when timestampdiff(month , p.updated_at, current_timestamp) < 12\n" +
            "           then concat(timestampdiff(month, p.updated_at, current_timestamp),'개월 전')\n" +
            "\n" +
            "           else concat(timestampdiff(year , p.updated_at, current_timestamp), '년 전')\n" +
            "       end postCreatedDate,\n" +
            "       group_concat(pi.idx) postImageIdx,\n" +
            "       group_concat(pi.image) postimage,\n" +
            "       group_concat(pi.image_number) postImageNumber\n" +
            "\n" +
            "from (select idx, content, updated_at ,user_idx from post where status ='ACTIVE') p\n" +
            "    left join (select idx, image,image_number, post_idx from post_image where status ='ACTIVE') pi\n" +
            "    on p.idx = pi.post_idx\n" +
            "    join (select idx, nick_name, image from user where status ='ACTIVE') u\n" +
            "    on p.user_idx = u.idx\n" +
            "group by p.idx\n" +
            "order by p.idx DESC", nativeQuery = true)
    List<GetPostsRes> getPosts(Pageable pageable);




    /* 8. 게시글 정보 수정 API */
    @Modifying
    @Transactional
    @Query(value="update Post p set p.content = :content where p.idx = :postIdx and p.status = 'ACTIVE'\n")
    void modifyPost(@Param("content") String content, @Param("postIdx") Long postIdx);


    /* 9. 게시글 삭제 API */
    @Modifying
    @Transactional
    @Query(value="update Post p set p.status = 'INACTIVE' where p.idx = :postIdx and p.status = 'ACTIVE' ")
    void deletePost(@Param("postIdx") Long postIdx);




    /* 10. 특정 게시글 조회 API */
    @Query(value="select u.idx userIdx,\n" +
            "       u.nick_name userNickName,\n" +
            "       u.image userimage,\n" +
            "       p.idx postIdx,\n" +
            "       p.content postContent,\n" +
            "       group_concat(pi.idx) postImageIdx,\n" +
            "       group_concat(pi.image) postimage,\n" +
            "       group_concat(pi.image_number) postImageNumber\n" +
            "\n" +
            "from (select idx, content ,user_idx from post where status ='ACTIVE') p\n" +
            "    left join (select idx, image,image_number, post_idx from post_image where status ='ACTIVE') pi\n" +
            "    on p.idx = pi.post_idx\n" +
            "    join (select idx, nick_name, image from user where status ='ACTIVE') u\n" +
            "    on p.user_idx = u.idx\n" +
            "where p.idx = :postIdx", nativeQuery = true)
    GetPostRes getPost(@Param("postIdx") Long postIdx);





    /* 15. 특정 게시글 정보 조회 (전체 댓글 조회 API) */
    @Query(value="select p.idx as postIdx,\n" +
            "       u.idx as writerIdx,\n" +
            "       u.image as writerImage,\n" +
            "       u.nick_name as writerNickName,\n" +
            "       p.content postContent,\n" +
            "       case when timestampdiff(minute, p.updated_at, current_timestamp) < 60\n" +
            "           then concat(timestampdiff(minute, p.updated_at, current_timestamp),'분')\n" +
            "\n" +
            "           when timestampdiff(hour , p.updated_at, current_timestamp) < 24\n" +
            "           then concat(timestampdiff(hour, p.updated_at, current_timestamp),'시간')\n" +
            "\n" +
            "           when timestampdiff(day , p.updated_at, current_timestamp) < 30\n" +
            "           then concat(timestampdiff(day, p.updated_at, current_timestamp),'일')\n" +
            "\n" +
            "           else DATE_FORMAT(p.updated_at, '%c월 %e일')\n" +
            "       end postCreatedTime\n" +
            "\n" +
            "from (select idx, content , updated_at, user_idx from post where status ='ACTIVE') p\n" +
            "    join (select idx, nick_name, image from user where status ='ACTIVE') u\n" +
            "    on p.user_idx = u.idx\n" +
            "where p.idx = :postIdx", nativeQuery = true)
    GetPostToCommentRes getPostToComment(@Param("postIdx") Long postIdx);









}
