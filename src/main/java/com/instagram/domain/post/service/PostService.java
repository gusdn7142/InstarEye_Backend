package com.instagram.domain.post.service;


import com.instagram.domain.post.dao.PostDao;
import com.instagram.domain.post.dao.PostImageDao;
import com.instagram.domain.post.domain.Post;
import com.instagram.domain.post.domain.PostImage;
import com.instagram.domain.post.dto.GetPostRes;
import com.instagram.domain.post.dto.GetPostsRes;
import com.instagram.domain.user.dao.UserDao;
import com.instagram.domain.user.domain.User;
import com.instagram.global.error.BasicException;
import com.instagram.global.util.Security.Secret;
import com.instagram.infra.aws.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.instagram.global.error.BasicResponseStatus.*;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostDao postDao;
    private final PostImageDao postImageDao;
    private final UserDao userDao;
    private final AwsS3Service awsS3Service;


    /* 6. 게시글 작성 */
    @Transactional(rollbackFor = {Exception.class})
    public String createPost(Long userIdX, String content, List<Integer> imageNumberlist, List<MultipartFile> multipartFile ) throws BasicException {

        //DB에 게시글 등록 (내용 ,이미지, 이미지 번호)
        try{
            User Writer = userDao.findByIdx(userIdX);

            //post DB에 저장
            Post postCreation = postDao.save(Post.builder()
                    .content(content)
                    .user(Writer)
                    .build());

            //postImage DB에 저장
            List<PostImage> postImageListCreation = new ArrayList<>();
            List<String> UUID_fileNameList = new ArrayList<>();;

            for(int i=0; i<imageNumberlist.size(); i++){
                //UUID가 적용된 이미지 파일명 리턴
                UUID_fileNameList.add(awsS3Service.createFileNameToDB(multipartFile.get(i)));            //UUID.randomUUID().toString() + "-" + multipartFile.get(i).getOriginalFilename();   //이미지 파일명에 UUID 적용

                PostImage postImageCreation = PostImage.builder()
                        .image(Secret.AWS_S3_CONNECT_URL+UUID_fileNameList.get(i))
                        .imageNumber(imageNumberlist.get(i))
                        .post(postCreation)
                        .build();
                postImageListCreation.add(postImageCreation);
            }

            postImageDao.saveAll(postImageListCreation);

            //S3에 이미지 파일 업로드
            for(int i=0; i<imageNumberlist.size(); i++) {
                URI imageUrl = awsS3Service.uploadFile(multipartFile.get(i), UUID_fileNameList.get(i));  //이미지 파일과 UUID가 적용된 파일명 인수로 전달
            }

            return "게시글 등록 성공";
        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_POST);  //DB에 게시글 등록 실패
        }
    }


    /* 7. 전체 게시글 조회 */
    public List<GetPostsRes> getPosts(Pageable pageable, Long userIdx) throws BasicException {

        try {
            List<GetPostsRes> getPostsRes = postDao.getPosts(pageable, userIdx);

            return getPostsRes;

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_FAIL_GET_POSTS);  //"DB에서 게시글 조회 실패"
        }
    }



    /* 8. 게시글 수정 API */
    @Transactional(rollbackFor = {Exception.class})
    public String modifyPost(Long postIdx, String content, List<MultipartFile> multipartFile, List<Integer> imageNumberlist, Long userIdx) throws BasicException {

        Post postBefore = postDao.findByIdx(postIdx);
        List<PostImage> PostImageBeforeList = postImageDao.findByPost(postBefore);

        //게시글 삭제 여부 조회
        if(postBefore == null){
            throw new BasicException(RES_ERROR_POSTS_DELETE_POST);    //"삭제된 게시글"
        }

        //게시글 작성자 일치 여부 확인
        User Writer = userDao.findByIdx(userIdx);
        if(postBefore.getUser() != Writer){
            throw new BasicException(RES_ERROR_POSTS_NOT_SAME_POST);    //"개시글 작성자 불일치 오류"
        }

        //게시글 내용 변경
        if(content != null) {
            try {
                postBefore.updateContent(content);
            } catch (Exception exception) {
                throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_POSTS_CONTENT);   //"게시글 내용 변경 오류"
            }
        }

        //게시글 이미지 정보 변경
        List<String> UUID_fileNameList = new ArrayList<>();;
        List<PostImage> postImageListCreation = new ArrayList<>();

        if(imageNumberlist != null) {
            try {
                //DB에서 기존 이미지 삭제
                postImageDao.deletePostImage(postBefore);

                //DB에 새로운 이미지 등록
                for(int i=0; i<imageNumberlist.size(); i++) {
                    UUID_fileNameList.add(awsS3Service.createFileNameToDB(multipartFile.get(i)));         //UUID가 적용된 새로운 이미지 파일명 리턴

                    PostImage postImageCreation = PostImage.builder()
                            .image(Secret.AWS_S3_CONNECT_URL+UUID_fileNameList.get(i))
                            .imageNumber(imageNumberlist.get(i))
                            .post(postBefore)
                            .build();
                    postImageListCreation.add(postImageCreation);
                }
                postImageDao.saveAll(postImageListCreation);
            }
            catch (Exception exception) {
                throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_POSTS_IMAGE);   //"게시글 이미지 변경 오류"
            }

            //S3에 새로운 이미지 파일 업로드
            for(int i=0; i<imageNumberlist.size(); i++) {
                URI imageUrl = awsS3Service.uploadFile(multipartFile.get(i), UUID_fileNameList.get(i));  //이미지 파일과 UUID가 적용된 파일명 인수로 전달
            }

            //S3에서 변경 전 이미지 파일 삭제
            try {
                for(int i=0; i<PostImageBeforeList.size(); i++) {
                    String beforeS3_fileName = PostImageBeforeList.get(i).getImage().replace(Secret.AWS_S3_CONNECT_URL, "");
                    awsS3Service.deleteFile(beforeS3_fileName);
                }
            }
            catch(Exception exception){    //이전 이미지 파일 삭제 실패시 업로드된 새로운 파일을 삭제
                for(int i=0; i<imageNumberlist.size(); i++) {
                    awsS3Service.deleteFile(UUID_fileNameList.get(i));
                }
                throw new BasicException(S3_ERROR_FAIL_DELETE_FILE);  //"S3에서 파일 삭제 실패"
            }
        }
        return "게시글 수정 성공";
    }



    /* 9. 게시글 삭제 API -  */
    @Transactional(rollbackFor = {Exception.class})
    public void deletePost(Long postIdx, Long userIdx) throws BasicException {

        //게시글 삭제 여부 조회 (유저가 계속 클릭시..)
        Post postDelete = postDao.findByIdx(postIdx);
        if(postDelete == null){             //게시글이 삭제되었다면..
            throw new BasicException(RES_ERROR_POSTS_DELETE_POST);    //"삭제된 게시글"
        }

        //게시글 작성자 일치 여부 확인
        User Writer = userDao.findByIdx(userIdx);
        if(postDelete.getUser() != Writer){
            throw new BasicException(RES_ERROR_POSTS_NOT_SAME_POST);    //"개시글 작성자 불일치 오류"
        }

        List<PostImage> PostImageDeleteList = postImageDao.findByPost(postDelete);

        try{
            //게시글 정보 삭제
            postDao.deletePost(postIdx);

            //게시글 이미지 정보 삭제
            postImageDao.deletePostImage(postDelete);


        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_DELETE_POSTS);   //'DB에서 게시글 삭제 실패'
        }


        //S3에서 이미지 파일 삭제
        for(int i=0; i<PostImageDeleteList.size(); i++) {
            String fileName = PostImageDeleteList.get(i).getImage().replace(Secret.AWS_S3_CONNECT_URL, "");
            awsS3Service.deleteFile(fileName);
        }

    }


    /* 10. 특정 게시글 조회 */
    public GetPostRes getPost(Long postIdx) throws BasicException {

        //게시글 삭제 여부 조회
        Post postDelete = postDao.findByIdx(postIdx);
        if(postDelete == null){             //게시글이 삭제되었다면..
            throw new BasicException(RES_ERROR_POSTS_DELETE_POST);    //"삭제된 게시글"
        }

        try {
            GetPostRes getPostRes = postDao.getPost(postIdx);

            return getPostRes;

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_FAIL_GET_POSTS);  //"DB에서 게시글 조회 실패"
        }

    }






}
