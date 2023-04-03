package com.instagram.domain.post.controller;

import com.instagram.domain.post.dao.PostDao;
import com.instagram.domain.post.dao.PostImageDao;
import com.instagram.domain.post.domain.Post;
import com.instagram.domain.post.domain.PostImage;
import com.instagram.domain.post.dto.GetPostRes;
import com.instagram.domain.user.dao.UserDao;
import com.instagram.domain.user.domain.PrivacyPolicyStatus;
import com.instagram.domain.user.domain.User;
import com.instagram.global.error.BasicResponse;
import com.instagram.global.util.Security.Secret;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import java.util.Date;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
class PostControllerTest {

    //테스트할 Controller와 DAO에 대한 의존성 주입
    @Autowired
    private PostController postController;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private UserDao userDao;

    @Autowired
    private PostDao postDao;

    @Autowired
    private PostImageDao postImageDao;

    //테스트에서 사용될 객체 선언
    private User userCreation;
    private Post postCreation;
    private PostImage postImageCreation;


    @Test
    @DisplayName("getPost() 메서드 테스트")
    void getPostAndPostCache() {
        //given
        Long userIdx = userCreation.getIdx();
        Long postIdx = postCreation.getIdx();

        //when
        BasicResponse<GetPostRes> response  = postController.getPost(userIdx, postIdx);                 //게시글 조회 및 새로운 캐시 생성
        BasicResponse<GetPostRes> cacheResponse = (BasicResponse<GetPostRes>) cacheManager              //postIdx 이름의 캐시 값 조회
                .getCache("postCache")
                .get(postIdx)
                .get();

        //then
        assertThat(response.getResult()).isNotNull();                              //response 값이 null이 아닌지 확인
        assertThat(cacheResponse.getResult()).isNotNull();                        //캐시 값이 null이 아닌지 확인
        assertThat(response.getResult()).isEqualTo(cacheResponse.getResult());    //response와 cacheResponse의 값이 같은지 확인
    }


    //각각의 테스트 메서드가 실행되기 전에 호출
    @BeforeEach
    void before(){
        //사용자 정보 DB에 등록
        userCreation = userDao.save(User.builder()
                .phone("01000000000")
                .name("그릿지11")
                .password("asdf1134")
                .birthDay(new Date())
                .privacyPolicyStatus(PrivacyPolicyStatus.AGREE)
                .nickName("gridge11")
                .email("")
                .build());
        //게시글 정보 DB에 등록
        postCreation = postDao.save(Post.builder()
                .content("안녕하세요. 그릿지 11입니다.")
                .user(userCreation)
                .build());
        //게시글의 이미지 정보 DB에 등록
        postImageCreation = postImageDao.save(PostImage.builder()
                .image(Secret.AWS_S3_CONNECT_URL+"dfasd15156a5sd5d1.png")
                .imageNumber(1)
                .post(postCreation)
                .build());
    }
}