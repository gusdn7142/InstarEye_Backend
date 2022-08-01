package com.instagram.domain.post.dao;

import com.instagram.domain.post.domain.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostImageDao extends JpaRepository<PostImage, Long> {


}
