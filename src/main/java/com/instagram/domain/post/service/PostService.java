package com.instagram.domain.post.service;


import com.instagram.domain.post.dao.PostDao;
import com.instagram.domain.post.dao.PostImageDao;
import com.instagram.domain.post.domain.Post;
import com.instagram.domain.post.domain.PostImage;
import com.instagram.domain.post.dto.GetPostsRes;
import com.instagram.domain.user.dao.UserDao;
import com.instagram.domain.user.domain.User;
import com.instagram.global.error.BasicException;
import com.instagram.global.util.Security.Secret;
import com.instagram.infra.aws.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestPart;
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
    public String createProduct(Long userIdX, String content, List<Integer> imageNumber, List<MultipartFile> multipartFile ) throws BasicException {


        //DB에 게시글 등록 (내용 ,이미지, 이미지 번호)
        try{
            User Writer = userDao.findByIdx(userIdX);

            //post DB에 저장
            Post postCreation = new Post();
            postCreation.setUser(Writer);
            postCreation.setContent(content);
            postDao.save(postCreation);


            //postImage DB에 저장
            List<PostImage> postImageListCreation = new ArrayList<>();
            List<String> UUID_fileNameList = new ArrayList<>();;

            for(int i=0; i<imageNumber.size(); i++){
                PostImage postImageCreation = new PostImage();   //내부에서 postImageIdx를 반복 횟수만큼 생성 (for문에 꼭 포함해야 한다)
                postImageCreation.setPost(postCreation);
                postImageCreation.setImageNumber(imageNumber.get(i));

                //UUID가 적용된 이미지 파일명 리턴
                UUID_fileNameList.add(awsS3Service.createFileNameToDB(multipartFile.get(i)));            //UUID.randomUUID().toString() + "-" + multipartFile.get(i).getOriginalFilename();   //이미지 파일명에 UUID 적용
                postImageCreation.setImage(Secret.AWS_S3_CONNECT_URL+UUID_fileNameList.get(i));

                postImageListCreation.add(postImageCreation);
            }

            postImageDao.saveAll(postImageListCreation);



            //S3에 이미지 파일 업로드
            for(int i=0; i<imageNumber.size(); i++) {
                URI imageUrl = awsS3Service.uploadFile(multipartFile.get(i), UUID_fileNameList.get(i));  //이미지 파일과 UUID가 적용된 파일명 인수로 전달
            }


            return "게시글 등록 성공";


        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_POST);  //게시글 등록 실패
        }



    }


    /* 7. 전체 게시글 조회 */
    public List<GetPostsRes> getPosts() throws BasicException {


        try {
            List<GetPostsRes> getPostsRes = postDao.getPosts();

            return getPostsRes;

        } catch (Exception exception) {
            //System.out.println(exception);
            throw new BasicException(DATABASE_ERROR_FAIL_GET_POSTS);  //"DB에서 게시글 조회 실패"
        }



    }






}
