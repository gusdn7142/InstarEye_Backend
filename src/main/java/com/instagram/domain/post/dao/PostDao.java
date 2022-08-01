package com.instagram.domain.post.dao;

import com.instagram.domain.post.domain.Post;
import com.instagram.domain.post.dto.GetPostsRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostDao extends JpaRepository<Post, Long> {


    /* 11. 전체 게시글 조회 API */
    @Query(value="select u.idx writerIdx,\n" +
            "       u.nick_name writerNickName,\n" +
            "       u.image image,\n" +
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
            "    join (select idx, image,image_number, post_idx from post_image where status ='ACTIVE') pi\n" +
            "    on p.idx = pi.post_idx\n" +
            "    join (select idx, nick_name, image from user where status ='ACTIVE') u\n" +
            "    on p.user_idx = u.idx\n" +
            "group by p.idx\n" +
            "order by p.idx DESC", nativeQuery = true)
    List<GetPostsRes> getPosts();



}
