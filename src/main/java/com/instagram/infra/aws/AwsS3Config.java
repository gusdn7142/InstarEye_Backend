package com.instagram.infra.aws;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;







/**
 * S3 config 파일
 * - S3에 접근할 떄 어떤 IAM을 통해 접근하고, 어떤 region을 통해 접근하는지 등을 설정
 */

@Configuration  //스프링 컨테이너로 등록
public class AwsS3Config {

    //applicaiton.yml 파일의 aws에 설정된 access-key 값을 accessKey 변수에 주입
    @Value("${cloud.aws.credentials.access-key}")  //IAM 계정의 accessKey 값 설정
    private String accessKey;

    //applicaiton.yml 파일의 aws에 설정된 secret-key 값을 secretKey 변수에 주입
    @Value("${cloud.aws.credentials.secret-key}")  //IAM 계정의 secretKey 값 설정
    private String secretKey;

    //applicaiton.yml 파일의 aws에 설정된 static 값을 region 변수에 주입
    @Value("${cloud.aws.region.static}")  //사용하는 region 명 설정
    private String region;

    @Bean  //Bean 등록 및 의존관계 주입
    public AmazonS3Client amazonS3Client() {

        //AmazonS3Config 설정 클래스 생성 : accessKey와 secretKey를 이용하여 자격증명 객체를 얻습니다.
        BasicAWSCredentials awsCreds = new BasicAWSCredentials(accessKey, secretKey);


        return (AmazonS3Client) AmazonS3ClientBuilder.standard()
                .withRegion(region)     //region을 설정
                .withCredentials(new AWSStaticCredentialsProvider(awsCreds)) //자격증명을 통해 S3 Client를 가져옵니다.
                .build();
    }


}
