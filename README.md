
# 📝 프로젝트 소개
> 백엔드 개발자 혼자서 진행한 [인스타그램 서비스](https://www.instagram.com/) API 서버 클론 프로젝트입니다.  
- 프로젝트 목적 : 외주 실력을 확인하는 소프트스퀘어드 주관 그릿지 테스트 챌린지(GTC)
- 제작 기간 : 2022년 7월 25일 ~ 2023년 4월 3일 
- 서버 개발자 : 뎁스 (본인)

</br>

## 💁‍♂️ Wiki
- ✍ [개발일지](https://fir-lancer-6bb.notion.site/Rest-API-1d79c1f4fe524863a63ebfc4287dce9a#aa0f1a651bee45eeab785c71934d724f)
- 📰 [API 명세서](https://fir-lancer-6bb.notion.site/API-1d94156d9f984832ba21b023aa5716f1)
- 📦 [ERD 설계도](https://user-images.githubusercontent.com/62496215/183288506-76da300b-f533-4cfd-ae43-70c8a07cbfbf.png)    
- 📁 [디렉토리 구조](https://github.com/gusdn7142/Instagram_Clone_Server/wiki/%F0%9F%93%81-Directory-Structure)
- 📽 시연 영상 : API 명세서의 postman 실행 결과 화면으로 대체  
- 📋️ 개발가이드와 화면설계서 : 저작권으로 인해 비공개

</br>

## 🛠 사용 기술
#### `Back-end`
  - Java 11
  - Spring Boot 2.7.2
  - Gradle 7.5
  - Spring Data JPA
#### `DevOps`  
  - AWS EC2 (Ubuntu 20.04)  
  - AWS RDS (Mysql 8.0)
  - AWS S3
  - Nginx
  - GitHub
  - Swagger
  - Docker

#### `Etc`  
  - JWT
  - Postman

</br>

## 📦 ERD 설계도
![Instagram_ERD](https://user-images.githubusercontent.com/62496215/183288506-76da300b-f533-4cfd-ae43-70c8a07cbfbf.png)



</br>




## 🔩 시스템 구성도
### 1. 전체 서비스 구조  
![Architecture](https://user-images.githubusercontent.com/62496215/230097536-327ccbfa-db2e-42f6-9b72-70ca842ab308.png)

### 2. 서버 동작 흐름 
![그림1](https://user-images.githubusercontent.com/62496215/227151222-2e2f9868-8f0f-41fc-9f35-95638b24c187.png)
#### 1️⃣ Client
- https://in-stagram.site/ 주소를 가진 Server에 resource 요청
- HTTP 메서드 활용 : Post, Patch, Get   

#### 2️⃣ DispatcherServlet
- 핸들러 매핑정보를 통해 요청 URL에 매핑된 핸들러(컨트롤러) 조회
- 해당 핸들러를 실행할 수 있는 핸들러 어댑터 조회
- (사용자 인가 절차가 필요한 URL이면) 핸들러 인터셉터 호출
- 핸들러 어댑터를 통해 핸들러(컨트롤러) 호출

#### 3️⃣ Interceptor
- 형식적 Validation 처리 (pathVariable 변수 한정)
    - 파라미터로 입력받은 모든 pathVariable 변수를 조회
    - 모든 pathVariable 변수에 "타입 오류"와 "미 입력"에 대한 예외 처리 
    - 오류 발생시 예외 메시지(+코드)를 정상 응답("200")으로 BasicException 객체에 담아 @ControllerAdvice에 예외를 전달
    - @ExceptionHandler로 예외를 받아 예외메시지(+코드)를 BasicResponse 객체에 담아 클라이언트에게 응답 
- 사용자 인가 절차
    - 헤더로 입력 받은 accessToken과 파리미터로 입력받은 User의 idx 조회
    - User의 idx와 accessToken에서 추출한 userIdx와 일치하는지 확인
    - 일치한다면 컨트롤러로 이동, 일치하지 않다면 예외를 @ControllerAdvice와 @ExceptionHandler로 전달하여 예외메시지(+코드)를 BasicResponse 객체에 담아 클라이언트에게 응답
- 사용자 인가 절차에서 제외되는 URI 
    - 로그인 API (/users), 회원가입(/users/login), 카카오 회원가입(/users/kakao), 카카오 로그인(/users/kakao-login), 개인정보 처리방침 재동의 API (/users/*/privacy-policy-reagree)

#### 4️⃣ Controller
- 클라이언트의 요청 값을 조회  (String to AnyType 컨버터 자동 적용)
    - @RequestBody : JSON 형식으로 DTO 객체에 매핑
    - @Pathvariable : 파라미터 변수와 매핑
    - @RequestPart : form 형식으로 변수에 매핑
- 형식적 Validation 처리
    - 요청받은 데이터(ex,DTO 객체)를 Bean Validation(@Valid)혹은 조건문을 통해 타입과 형식 검증 수행
    - 오류 발생시 예외 메시지(+코드)를 정상 응답("200")으로 BasicResponse 객체에 담아 응답
- 결과 응답
    - Service 계층에서 넘어온 로직 처리 결과(자원 or 예외메시지)를 BasicResponse 객체에 담아 클라이언트에게 응답 

#### 5️⃣ Service
- 의미적 Validation 처리
    - DB 서버의 CRUD 혹은 AWS S3의 파일 업로드∘삭제 로직 수행시에 발생할 수 있는 예외를 처리
    - 오류 발생시 예외 메시지(+코드)를 정상 응답("200")으로 BasicException 객체에 담아 Controller 계층에 응답 
- 트랜잭션 처리
    - Insert, Update, Delete Query가 생성되는 메서드에 @Transactional 어노테이션 적용
- 결과 리턴
    - 주로 Dao 계층에서 DB 서버의 CRUD 명령을 수행한 결과를 다양한 DTO 타입으로 리턴 받아 Controller 계층에 리턴

#### 6️⃣ Dao
- 쿼리 수행 
    - JPA (@Query) 활용 : 주로 Native Query(SQL) 혹은 UnNative Query(jpql)을 활용해 DB 쿼리 로직 수행 
    - Join, SubQuery, group_concat, IFNULL, FORMAT, Concat 등의 Mysql 문법 활용
- 결과 리턴
    - 주로 DTO 객체, Entity, void 등의 형식으로 Service 계층에 리턴

#### 🔁 Scheduler
- 스프링 스케줄러 동작 : 매일 자정 모든 사용자의 개인정보 처리방침 동의 시기를 DB에서 확인하여 가입 일에서 1년이 지난 사용자의 동의 상태를 만료


</br>


## 🔎 핵심 기능 및 담당 기능
>인스타그램 서비스의 핵심기능은 피드 작성과 조회입니다.  
>구현한 핵심 기능은 이 [페이지](https://fir-lancer-6bb.notion.site/258e95f01ee24f11a18382fb2fb54076)를 참고해 주시면 감사합니다.   

</br>

## 🌟 트러블 슈팅
>핵심 트러블 슈팅은 이 [페이지](https://fir-lancer-6bb.notion.site/b3ecb1a81c84481884dcb3c3afcbbe7c)를 참고해 주시면 감사합니다.    


  
  
</br>

## ❕ 회고 / 느낀점
- 외주를 할수 있는 역량이 되는지 실력을 확인함과 동시에 CTO님께 피드백을 받아볼수 있는 좋은 기회여서 이 챌린지에 참여하게 되었습니다.
- 개발가이드와 화면설계서의 요구사항을 토대로 기능을 하나씩 구현해 나감으로써 성취감을 느낄수 있었습니다.
- 기존에는 API 명세서로 구글 스프레드시트를 활용했었기 때문에 이번에는 노션으로 API 명세서를 만들어 보았으나, 유지보수 측면에서 코드를 변경하면 노션 페이지를 계속 수정해야 하는 번거로움이 발생하여 Swegger UI를 도입하였는데, 다음 프로젝트 때에는 postman으로 API 명세서를 만드는것도 시도해 보고 싶습니다.
- 대부분의 API에 적용되는 사용자 인가절차를 인터셉터에서 공통로직으로 처리하도록 구현한 것과 스케줄러를 통해 개인정보처리방침의 동의상태 일자를 일 단위로 확인해 가입일을 기준으로 1년마다 갱신하는 로직을 구현해본것이 기억에 남습니다.    
                
                
</br>

## 👩‍💻 리팩토링 계획
- [x] 회원탈퇴시 User 테이블 이외의 연관된 다수의 테이블에서 Update 쿼리문이 실행되어 응답시간이 약 26초가 걸리는 이슈 해결  
      =>엔티티 객체의 delete엔티티() 메서드를 통해 회원탈퇴 로직을 구현함으로써 기존의 과다한 조인 전략으로 성능이 좋지 않았던 SQL문을 제거
- [x] @Pathvariable로 입력받는 모든 경로 변수(idx)에 유효성 검사 적용 (ex, 입력값 필터링) 
- [x] Docker를 이용해 Spring Boot 애플리케이션 배포
- [x] 모든 API에 Swagger 적용
- [ ] JPQL(@Query) 코드를 Query DSL 코드로 리팩토링  
- [ ] 테스트 코드 도입
- [ ] 프론트엔드 개발자와 협업하여 API 연결 및 이슈 처리
- [ ] Response 구조의 Best Practice 연구  
- [ ] 휴면계정과 차단계정 관리를 위한 DB 설계와 API 구현 
- [ ] 게시글과 댓글 신고 API 구현
- [ ] Admin 도메인 DB 설계 및 API 구현

