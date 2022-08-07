
# 📝 프로젝트 소개
> 백엔드 개발자 1명이서 진행한 [인스타그램 서비스](https://www.instagram.com/) 서버 클론 프로젝트입니다.  
- 프로젝트 목적 : 외주 실력을 확인하는 소프트스퀘어드 주관 그릿지 테스트 챌린지(GTC)
- 제작 기간 : 2022년 7월 25일 ~ 8월 7일 
- 서버 개발자 : 뎁스(본인)

</br>

## 💁‍♂️ Wiki
- ✍ [개발일지](추가예정)
- 📰 [API 명세서](https://www.notion.so/API-1d94156d9f984832ba21b023aa5716f1)
- 📦 [ERD 설계도](https://aquerytool.com/aquerymain/index/?rurl=b0f4f366-b187-4bed-b854-ea1b30aec38b)    
    - 비밀번호 : ws3x7t   
- 📁 [디렉토리 구조](https://github.com/gusdn7142/TodayFruit_Clone_Server/wiki/%F0%9F%93%81-Directory-Structure)
- 📽 시연 영상 : 추가예정


</br>

## 🛠 사용 기술
#### `Back-end`
  - Java 11
  - Spring Boot 2.7.2
  - Gradle 7.5
  - Spring Data JPA
  - Spring Security
#### `DevOps`  
  - AWS EC2 (Ubuntu 20.04)  
  - AWS RDS (Mysql 8.0)
  - AWS S3
  - Nginx
  - GitHub
  - Swagger
  - Docker (추가예정)

#### `Etc`  
  - JWT
  - postman

</br>

## 📦 ERD 설계도
![Instagram_ERD](https://user-images.githubusercontent.com/62496215/183281668-488bfbbb-4cf9-42c7-866e-f4ab7cf6752f.png)



</br>




## 🔎 시스템 구성도
- 서비스 구조  
![그림1](https://user-images.githubusercontent.com/62496215/183283405-5d94529a-8531-4041-bcf2-1c489c3142a0.png)

- 전체 동작 흐름  
![그림2](https://user-images.githubusercontent.com/62496215/183283787-7269efa6-aba1-455a-8945-315955fe3928.png)
1️⃣Interceptor : 



</br>


## 🔎 핵심 기능 및 담당 기능

>인스타그램 서비스의 핵심기능은 피드 작성과 조회입니다.  
>서비스의 세부적인 기능은 [API 명세서](https://www.notion.so/API-1d94156d9f984832ba21b023aa5716f1)를 참고해 주시면 감사합니다.   
- 구현한 기능 (도메인별)  
  - 사용자 : 회원가입 API, 로그인 API, 카카오 회원가입∘로그인 API, 프로필 조회∘수정 API, 회원탍퇴 API, 개인정보 처리방침 재동의 API
  - 게시글 : 게시글 작성∘수정∘삭제 API, 전체∘특정 게시글 조회 API
  - 채팅 : 채팅 메시지 전송 API, 채팅 내역 조회 API, 채팅 메시지 삭제 API,
  - 댓글 : 댓글 작성 API, 전체 댓글 조회 API, 댓글 삭제 API, 댓글 수정 API
  - 좋아요 : 게시글 좋아요 API, 게시글 좋아요 취소 API, 댓글 좋아요 API, 댓글 좋아요 취소 API 
  - 팔로우 요청 : 팔로우 요청 API, 팔로우 요청 취소 API, 팔로우 요청 거절 API
  - 팔로우 : 팔로우 API, 팔로우 취소 API, 팔로우 요청 승인 API


</br>

## 🌟 트러블 슈팅
추가 예정

</br>

## ❕ 회고 / 느낀점
추가예정



## 👩‍💻 리팩토링 계획
추가 예정


