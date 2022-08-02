package com.instagram.domain.post.dao;

import com.instagram.domain.post.domain.Post;
import com.instagram.domain.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

public interface PostImageDao extends JpaRepository<PostImage, Long> {

    /* 7. post 객체 통해 PostImage 엔티티 조회  */
    @Query(value="select pi from PostImage pi where pi.post = :post and pi.status = 'ACTIVE'")
    List<PostImage> findByPost(@Param("post") Post post);


    /* 8. 게시글 이미지 정보 수정 API */
    @Modifying
    @Transactional
    @Query(value="update PostImage pi set pi.image = :image where pi.idx = :postImageIdx and pi.post = :post and pi.status = 'ACTIVE'\n")
    void modifyPostImage(@Param("image") String image,
                         @Param("postImageIdx") Long postImageIdx,
                         @Param("post") Post post); // @Param("post") Post post);

    /* 9. 게시글 이미지 정보 삭제 API */
    @Modifying
    @Transactional
    @Query(value="update PostImage pi set pi.status = 'INACTIVE' where pi.post = :post and pi.status = 'ACTIVE' ")
    void deletePostImage(@Param("post") Post post);

}
