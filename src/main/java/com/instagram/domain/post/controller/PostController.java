package com.instagram.domain.post.controller;


import com.instagram.domain.post.dto.GetPostRes;
import com.instagram.domain.post.dto.GetPostsRes;
import com.instagram.domain.post.service.PostService;
import com.instagram.global.error.BasicException;
import com.instagram.global.error.BasicResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static com.instagram.global.error.BasicResponseStatus.*;


@RestController
@RequestMapping("posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;


    /**
     * 6. 게시글 작성 API
     * [POST] /posts/:userId
     * @return BaseResponse(responseMessage)
     */
    @PostMapping("/{userIdx}")      //, consumes = {"multipart/form-data"}
    public BasicResponse createPost(@PathVariable("userIdx") Long userIdx,
                                       @RequestPart(value = "content", required = false) String content,
                                       @RequestPart(value = "imageNumber", required = false) List<Integer> imageNumber,
                                       @RequestPart(value = "imageFile", required = false) List<MultipartFile> multipartFile) {



        /* 유효성 검사 구현부 */
        if(content == null || content.length() > 1000){
            return new BasicResponse(REQ_ERROR_INVALID_POSTS_CONTENT);
        }
        if(imageNumber == null || imageNumber.size() > 10 ){
            return new BasicResponse(REQ_ERROR_INVALID_POSTS_IMAGE_NUMBER);
        }
        if(multipartFile.isEmpty() || multipartFile.size() > 10) {
            return new BasicResponse(REQ_ERROR_INVALID_POSTS_IMAGE_FILE);
        }
        if(imageNumber.size() != multipartFile.size() ) {
            return new BasicResponse(REQ_ERROR_DIFFERENT_SIZE_IMAGE_FILE_AND_IMAGE_NUMBER);
        }

        for(int i=0; i<multipartFile.size(); i++){
            String fileExt = multipartFile.get(i).getOriginalFilename().substring(multipartFile.get(i).getOriginalFilename().lastIndexOf(".")+1).toLowerCase();

            //이미지 파일의 확장자가 git, jpg, jpeg, png가 아니면 예외 발생
            if(! "gif".equals(fileExt) && ! "jpg".equals(fileExt) && ! "jpeg".equals(fileExt) && ! "png".equals(fileExt)){
                return new BasicResponse(REQ_ERROR_INVALID_POSTS_IMAGE_FILE_EXTENSION);
            }

            //이미지 파일 크기 제한 (10MB)
            if(multipartFile.get(i).getSize() > 10485760){
                return new BasicResponse(REQ_ERROR_INVALID_POSTS_IMAGE_FILE_SIZE);
            }
        }
        /* 유효성 검사 구현 끝 */






        try {
            //DB에 게시글 등록
            String responseMessage = postService.createPost(userIdx, content, imageNumber, multipartFile);

            return new BasicResponse(responseMessage);
        } catch (BasicException exception) {
            return new BasicResponse(exception.getStatus());
        }


    }




    /**
     * 7. 전체 게시글 조회 API
     * [GET] /posts/all/:userIdx
     * @return BaseResponse(getPostRes)
     */

    @GetMapping("/all/{userIdx}")
    public BasicResponse getPosts(@PageableDefault(page = 0, size = 10)  Pageable pageable){  //, direction = Sort.Direction.DESC, sort = "p.idx"  ??

        try {

            //전체 게시글 조회
            List<GetPostsRes> getPostsRes = postService.getPosts(pageable);

            return new BasicResponse(getPostsRes);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }


    }



    /**
     * 8. 게시글 수정 API
     * [PATCH] /post/:userId/:postId
     * @return BaseResponse()
     */
    // Body
    @PatchMapping("/{userIdx}/{postIdx}")
    public BasicResponse modifyPost(@PathVariable("userIdx") Long userIdx  ,
                                    @PathVariable("postIdx") Long postIdx,
                                    @RequestPart(value = "postImageIdx", required = false) List<Long> postImageIdxList,
                                    @RequestPart(value = "content", required = false) String content,
                                    @RequestPart(value = "imageFile", required = false) List<MultipartFile> multipartFile,
                                    @RequestPart(value = "imageNumber", required = false) List<Integer> imageNumber) {

        /* 유효성 검사 구현부 */
        if(content == null || content.length() > 1000){
            return new BasicResponse(REQ_ERROR_INVALID_POSTS_CONTENT);
        }
        if(imageNumber == null || imageNumber.size() > 10 ){
            return new BasicResponse(REQ_ERROR_INVALID_POSTS_IMAGE_NUMBER);
        }
        if(multipartFile.isEmpty() || multipartFile.size() > 10) {
            return new BasicResponse(REQ_ERROR_INVALID_POSTS_IMAGE_FILE);
        }
        if(imageNumber.size() != multipartFile.size() ) {
            return new BasicResponse(REQ_ERROR_DIFFERENT_SIZE_IMAGE_FILE_AND_IMAGE_NUMBER);
        }



        for(int i=0; i<multipartFile.size(); i++){
            String fileExt = multipartFile.get(i).getOriginalFilename().substring(multipartFile.get(i).getOriginalFilename().lastIndexOf(".")+1).toLowerCase();

            //이미지 파일의 확장자가 git, jpg, jpeg, png가 아니면 예외 발생
            if(! "gif".equals(fileExt) && ! "jpg".equals(fileExt) && ! "jpeg".equals(fileExt) && ! "png".equals(fileExt)){
                return new BasicResponse(REQ_ERROR_INVALID_POSTS_IMAGE_FILE_EXTENSION);
            }

            //이미지 파일 크기 제한 (10MB)
            if(multipartFile.get(i).getSize() > 10485760){
                return new BasicResponse(REQ_ERROR_INVALID_POSTS_IMAGE_FILE_SIZE);
            }
        }
        /* 유효성 검사 구현 끝 */



        try{
            //DB에 게시글 정보 수정
            String responseMessage = postService.modifyPost(postIdx ,content, postImageIdxList, multipartFile, imageNumber);

            return new BasicResponse(responseMessage);
        } catch(BasicException exception){
            return new BasicResponse(exception.getStatus());
        }



    }



    /**
     * 9. 게시글 삭제 API
     * [PATCH] /posts/:userIdx/:postIdx/status
     * @return BaseResponse<String>
     */

    @PatchMapping("/{userIdx}/{postIdx}/status")
    public BasicResponse deletePost(@PathVariable("userIdx") Long userIdx, @PathVariable("postIdx") Long postIdx ) {


        try {


            //게시글 삭제
            postService.deletePost(postIdx);

            String result = "게시글 정보 삭제";
            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }





    /**
     * 10. 특정 게시글 조회 API
     * [GET] /posts/:userId/:postId
     * @return BaseResponse(getPostRes)
     */

    @GetMapping("/{userIdx}/{postIdx}")
    public BasicResponse getPost(@PathVariable("userIdx") Long userIdx, @PathVariable("postIdx") Long postIdx ){


        try {

            //특정 게시글 조회
            GetPostRes getPostRes = postService.getPost(postIdx);

            return new BasicResponse(getPostRes);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }


    }

















}
