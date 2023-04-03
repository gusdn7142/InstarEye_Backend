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
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
    @DisplayName("modifyPost() 메서드 테스트")
    void modifyPostAndDeletePostCache(){
        //given
        Long userIdx = userCreation.getIdx();                                                  //변경할 userIdx
        Long postIdx = postCreation.getIdx();                                                  //변경할 postIdx
        String content = "안녕하세요. 그릿지 11999입니다.";                                     //변경할 게시글 내용 입력

        String filename = Secret.AWS_S3_CONNECT_URL+"post1234.png";
        String fileContent = "post1234 file";                                                 //변경할 게시글 내용 입력
        MultipartFile multipartFile = toMultipartFile(filename, fileContent);                 //String 타입 파일을 MultipartFile 타입으로 변환
        List<MultipartFile> multipartFiles = Arrays.asList(multipartFile);                    //변경할 이미지 파일들
        List<Integer> imageNumberlist = Arrays.asList(1);                                     //변경할 이미지 번호
        postController.getPost(userIdx, postIdx);      //postIdx 이름의 캐시 생성

        //when
        BasicResponse<String> response = postController.modifyPost(userIdx, postIdx, content, multipartFiles, imageNumberlist);       //게시글 수정 후 캐시값 갱신

        //then
        assertThat(response.getResult()).isEqualTo("게시글 수정 성공");                           //게시글 정보 수정 성공 확인
        assertThatThrownBy(() -> {
            BasicResponse<GetPostRes> cacheResponse = (BasicResponse<GetPostRes>) cacheManager        //캐시 값이 null인지 확인
                    .getCache("postCache")
                    .get(postIdx)
                    .get();
        }).isInstanceOf(NullPointerException.class);
    }

    //String 타입을 MultipartFile 타입으로 변환
    public static MultipartFile toMultipartFile(String filename, String content) {
        byte[] contentBytes = content.getBytes();          //content를 String 타입에서 Byte[] 타입으로 변환
        return new MockMultipartFile(filename, filename, MediaType.IMAGE_PNG_VALUE, contentBytes);  //MockMultipartFile 객체 생성
    }


    @Test
    @DisplayName("deletePost() 메서드 테스트")
    void deletePostAndPostCache() {
        //given
        Long userIdx = userCreation.getIdx();
        Long postIdx = postCreation.getIdx();
        postController.getPost(userIdx, postIdx);   //새로운 캐시 생성

        //when
        BasicResponse<String> response = postController.deletePost(userIdx, postIdx);   //postIdx를 기준으로 게시글 정보와 캐시 정보 삭제

        //then
        assertThat(response.getResult()).isEqualTo("게시글 정보 삭제");  //게시글 정보 삭제유무 확인
        assertThatThrownBy(() -> {
            BasicResponse<GetPostRes> cacheResponse = (BasicResponse<GetPostRes>) cacheManager  //캐시 값이 null인지 확인회
                    .getCache("postCache")
                    .get(postIdx)
                    .get();
        }).isInstanceOf(NullPointerException.class);
    }


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