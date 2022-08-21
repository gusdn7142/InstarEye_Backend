package com.instagram.infra.aws;


import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.instagram.global.error.BasicException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URI;
import java.util.UUID;

import static com.instagram.global.error.BasicResponseStatus.*;

@Service
@RequiredArgsConstructor
public class AwsS3Service {


    @Value("${cloud.aws.s3.bucket}")
    private String bucket;  //버킷 이름
    private final AmazonS3 amazonS3;  //AmazonS3 객체


    // Amazon S3에 파일 업로드
    public URI uploadFile(MultipartFile imageFile, String UUID_fileName) throws BasicException  { //이미지 파일과 UUID가 적용된 파일명을 파라미터로 받아옴.

        ObjectMetadata objectMetadata = new ObjectMetadata();  //inputStream은 Byte로 전달이 되기 때문에 파일에 대한 정보를 추가하기 위해 ObjectMetadata 활용
        objectMetadata.setContentLength(imageFile.getSize());       //HTTP Header의 ContentLength와
        objectMetadata.setContentType(imageFile.getContentType());  //ContentType 지정

        //S3에 파일 업로드
        try {
                //(파일) inputstream 설정
                InputStream inputStream = imageFile.getInputStream();  //InputStream : 데이터가 들어오는 통로의 역할에 관해 규저앟고 있는 추상 클래스

                //S3에 오브젝트 업로드
                amazonS3.putObject(new PutObjectRequest(bucket, UUID_fileName, inputStream, objectMetadata)  //putObject("버킷이름", "파일 이름", inputStream객체, 파일에 대한 Header 정보)
                        .withCannedAcl(CannedAccessControlList.PublicRead));  //PublicRead 권한을 줌
        } catch (Exception exception) {
            throw new BasicException(S3_ERROR_FAIL_UPLOAD_FILE);  //"S3에 파일 업로드 실패"
        }

        //클라이언트에게 보내기 위해 S3에서 파일 uri 조회
        S3Object s3Object = amazonS3.getObject(bucket, UUID_fileName);   //S3에서 업로드된 해당 파일 조회
        URI s3Uri = s3Object.getObjectContent().getHttpRequest().getURI();

        return s3Uri;

    }


    //UUID를 적용한 이미지 파일명 리턴
    public String createFileNameToDB(MultipartFile imageFile) {
        String fileName = imageFile.getOriginalFilename();
        String UUID_fileName = UUID.randomUUID().toString() + "." + fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase(); //UUID+파일 확장자
        return UUID_fileName;  //UUID 파일명 리턴
    }




    //Amazon S3에 업로드 된 파일을 삭제
    public void deleteFile(String fileName) throws BasicException {
        try {
            amazonS3.deleteObject(bucket, fileName);   //S3에서 업로드된 해당 파일 삭제
        } catch (Exception exception) {
            throw new BasicException(S3_ERROR_FAIL_DELETE_FILE);  //"S3에서 파일 삭제 실패"
        }

    }













}
