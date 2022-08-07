
# 📝 프로젝트 소개
> 백엔드 개발자 1명이서 진행한 [인스타그램 서비스](https://www.instagram.com/) API 서버 클론 프로젝트입니다.  
- 프로젝트 목적 : 외주 실력을 확인하는 소프트스퀘어드 주관 그릿지 테스트 챌린지(GTC)
- 제작 기간 : 2022년 7월 25일 ~ 8월 7일 
- 서버 개발자 : 뎁스(본인)

</br>

## 💁‍♂️ Wiki
- ✍ [개발일지](https://fir-lancer-6bb.notion.site/API-1d79c1f4fe524863a63ebfc4287dce9a)
- 📰 [API 명세서](https://www.notion.so/API-1d94156d9f984832ba21b023aa5716f1)
- 📦 [ERD 설계도](https://aquerytool.com/aquerymain/index/?rurl=b0f4f366-b187-4bed-b854-ea1b30aec38b)    
    - 비밀번호 : ws3x7t   
- 📁 [디렉토리 구조](https://github.com/gusdn7142/Instagram_Clone_Server/wiki/%F0%9F%93%81-Directory-Structure)
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
  - Postman

</br>

## 📦 ERD 설계도
![Instagram_ERD](https://user-images.githubusercontent.com/62496215/183288506-76da300b-f533-4cfd-ae43-70c8a07cbfbf.png)



</br>




## 🔎 시스템 구성도
### 전체 서비스 구조  
![그림1](https://user-images.githubusercontent.com/62496215/183283405-5d94529a-8531-4041-bcf2-1c489c3142a0.png)

### 서버 동작 흐름  
![그림2](https://user-images.githubusercontent.com/62496215/183283787-7269efa6-aba1-455a-8945-315955fe3928.png)
#### 1️⃣ Client
- https://in-stagram.site/ 주소를 가진 Server에 resource 요청
- Post / Patch / Get 메서드 활용  
#### 2️⃣ Interceptor
- 로그인 인가 확인 절차
    - accessToken을 헤더로 입력받고 User의 idx 값을 파라미터로 입력받음.
    - User의 idx와 accessToken에서 추출한 userIdx와 일치하는지 확인
    - 일치한다면 컨트롤러로 이동, 일치하지 않다면 예외메시지 응답
- 로그인 인가 절차에서 제외되는 URI 
    - 로그인 API (/users), 회원가입(/users/login), 카카오 회원가입(/users/kakao) 카카오 로그인(/users/kakao-login), 개인정보 처리방침 재동의 API (/users/*/privacy-policy-reagree)

#### 3️⃣ Controller
- 형식적 Validation 처리
    - 요청받은 데이터(ex,DTO 객체)를 Bean Validation(@Valid)혹은 조건문을 통해 타입과 형식 검증 수행
    - 오류 발생시 예외 메시지(+코드)를 정상 응답("200")으로 BasicResponse 객체에 담아 응답
- 결과 응답
    - Service 계층에서 넘어온 로직 처리 결과(자원 or 예외메시지)를 BasicResponse 객체에 담아 클라이언트에게 응답 

#### 4️⃣ Service
- 의미적 Validation 처리
    - DB 서버의 CRUD 혹은 AWS S3의 파일 업로드∘삭제 로직 수행시에 발생할 수 있는 예외를 처리
    - 오류 발생시 예외 메시지(+코드)를 정상 응답("200")으로 BasicException 객체에 담아 Controller 계층에 응답 
- 트랜잭션 처리
    - @Transactional 어노테이션 적용 : 하나의 Service 로직에서 2개 이상의 쿼리 로직을 수행시 발생할 수 있는 에러에 대한 롤백 처리
- 결과 응답
    - 주로 DB 서버의 CRUD 명령을 수행한 결과를 다양한 타입으로 Controller 계층에 응답

#### 5️⃣ Dao
- 쿼리 수행 
    - JPA (@Query) 활용 : 주로 Native Query(SQL) 혹은 UnNative Query(jpql)을 활용해 DB 쿼리 로직 수행 
    - Join, SubQuery, group_concat, IFNULL, FORMAT, Concat 등의 Mysql 문법 활용
- 결과 응답
    - 주로 DTO 객체, Entity, void 등의 형식으로 Service 계층에 응답

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


