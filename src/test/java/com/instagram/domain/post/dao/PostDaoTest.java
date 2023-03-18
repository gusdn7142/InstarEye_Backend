package com.instagram.domain.post.dao;

import com.instagram.domain.post.domain.Post;
import com.instagram.domain.post.domain.PostImage;
import com.instagram.domain.post.dto.GetPostRes;
import com.instagram.domain.user.dao.UserDao;
import com.instagram.domain.user.domain.PrivacyPolicyStatus;
import com.instagram.domain.user.domain.User;
import com.instagram.global.util.Security.Secret;
import java.util.Date;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static com.instagram.global.util.Security.Secret.DEFAULT_USER_IMAGE;


@DataJpaTest
class PostDaoTest {

    @Autowired
    private UserDao userDao;
    @Autowired
    private PostDao postDao;
    @Autowired
    private PostImageDao postImageDao;

    @Test
    @DisplayName("PostDao의 getPost() 메서드 테스트")
    void getPost(){

        //given
        User userCreation = userDao.save(User.builder()
                .phone("01011345678")
                .name("그릿지11")
                .password("asdf1134")
                .birthDay(new Date())
                .privacyPolicyStatus(PrivacyPolicyStatus.AGREE)
                .nickName("gridge11")
                .email("")
                .build());
        Post postCreation = postDao.save(Post.builder()
                .content("안녕하세요. 그릿지 11입니다.")
                .user(userCreation)
                .build());
        PostImage postImageCreation1 = postImageDao.save(PostImage.builder()
                .image(Secret.AWS_S3_CONNECT_URL+"dfasd15156a5sd5d1.png")
                .imageNumber(1)
                .post(postCreation)
                .build());
        PostImage postImageCreation2 = postImageDao.save(PostImage.builder()
                .image(Secret.AWS_S3_CONNECT_URL+"dfasd15156a5sd5d2.png")
                .imageNumber(2)
                .post(postCreation)
                .build());

        //when
        GetPostRes getPostRes = postDao.getPost(postCreation.getIdx());

        //then
        Assertions.assertThat(getPostRes.getUserIdx()).isEqualTo(userCreation.getIdx());
        Assertions.assertThat(getPostRes.getUserNickName()).isEqualTo(userCreation.getNickName());
        Assertions.assertThat(getPostRes.getUserimage()).isEqualTo(DEFAULT_USER_IMAGE);
        Assertions.assertThat(getPostRes.getPostIdx()).isEqualTo(postCreation.getIdx());
        Assertions.assertThat(getPostRes.getPostImageIdx()).isEqualTo(postImageCreation1.getIdx() + "," + postImageCreation2.getIdx());
        Assertions.assertThat(getPostRes.getPostimage()).isEqualTo(postImageCreation1.getImage() + "," + postImageCreation2.getImage());
        Assertions.assertThat(getPostRes.getPostImageNumber()).isEqualTo(postImageCreation1.getImageNumber() + "," + postImageCreation2.getImageNumber());

    }
}