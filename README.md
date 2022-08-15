
# 📝 프로젝트 소개
> 백엔드 개발자 혼자서 진행한 [인스타그램 서비스](https://www.instagram.com/) API 서버 클론 프로젝트입니다.  
- 프로젝트 목적 : 외주 실력을 확인하는 소프트스퀘어드 주관 그릿지 테스트 챌린지(GTC)
- 제작 기간 : 2022년 7월 25일 ~ 8월 7일 
- 서버 개발자 : 뎁스 (본인)

</br>

## 💁‍♂️ Wiki
- ✍ [개발일지](https://fir-lancer-6bb.notion.site/API-1d79c1f4fe524863a63ebfc4287dce9a)
- 📰 [API 명세서](https://www.notion.so/API-1d94156d9f984832ba21b023aa5716f1)
- 📦 [ERD 설계도](https://aquerytool.com/aquerymain/index/?rurl=b0f4f366-b187-4bed-b854-ea1b30aec38b)    
    - 비밀번호 : ws3x7t   
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




## 🔩 시스템 구성도
### 전체 서비스 구조  
![그림1](https://user-images.githubusercontent.com/62496215/183283405-5d94529a-8531-4041-bcf2-1c489c3142a0.png)

### 서버 동작 흐름  
![그림2](https://user-images.githubusercontent.com/62496215/183283787-7269efa6-aba1-455a-8945-315955fe3928.png)
#### 1️⃣ Client
- https://in-stagram.site/ 주소를 가진 Server에 resource 요청
- HTTP 메서드 활용 : Post, Patch, Get   
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
>서비스의 세부적인 기능은 [API 명세서](https://www.notion.so/API-1d94156d9f984832ba21b023aa5716f1) 를 참고해 주시면 감사합니다.   
- 구현한 기능  
  - API  (도메인별 분류)
    - 사용자 : 회원가입 API, 로그인 API, 카카오 회원가입∘로그인 API, 프로필 조회∘수정 API, 회원퇴 API, 개인정보 처리방침 재동의 API
    - 게시글 : 게시글 작성∘수정∘삭제 API, 전체∘특정 게시글 조회 API
    - 채팅 : 채팅 메시지 전송 API, 채팅 내역 조회 API, 채팅 메시지 삭제 API,
    - 댓글 : 댓글 작성 API, 전체 댓글 조회 API, 댓글 삭제 API, 댓글 수정 API
    - 좋아요 : 게시글 좋아요 API, 게시글 좋아요 취소 API, 댓글 좋아요 API, 댓글 좋아요 취소 API 
    - 팔로우 요청 : 팔로우 요청 API, 팔로우 요청 취소 API, 팔로우 요청 거절 API
    - 팔로우 : 팔로우 API, 팔로우 취소 API, 팔로우 요청 승인 API
  - 스케줄러 
    - 개인정보 처리방침 동의여부 확인 (1년마다 갱신)

</br>

## 🌟 핵심 트러블 슈팅
<details>
<summary>도메인 서버 등록시 반영시간 관련 이슈</summary>
<div markdown="1">

- **Issue** :  도메인(https://in-stagram.site)을 구입 후 EC2의 공인 IP를 연결해 주었는데, 서버가 응답하지 않습니다.
- **Problem** : 공인 ip와 도메인간의 연결은 되었는데, 아직 도메인 주소 활성화가 되지 않아서 그런것 같습니다.
- **Solution** : 5시간 정도 기다린 후 도메인(https://in-stagram.site)이 연결되어 nignx 웹서버의 default 화면을 볼 수 있었습니다.
  
</div>
</details>

<details>
<summary> 스웨거 UI에 반영할 오류코드 설명 관련 이슈 </summary>
<div markdown="1">

- **Issue & Problem** : Status Code가 200일때 정상응답과 에러응답 설명을 같이 표기해야 하기 때문에 스웨거로 클라이언트와 협업시 불편을 겪을것을 예상되었습니다. 
![Swegger Error UI](https://user-images.githubusercontent.com/62496215/184532374-17cebd34-13b4-48f0-8693-160cad58673e.png)
- **Solution** : 이 프로젝트에서는 스웨거 대신 노션을 API 명세서로 활용하는것으로 대체

</div>
</details>


<details>
<summary>  @Query (JPQL) 사용시 이슈 </summary>
<div markdown="1">

- **Issue** : JPQL에서 group_concat()과 Select() 서브 쿼리문을 사용시 오류 발생 
- **Problem** : RDB 제약으로 컬렉션 형태로 엔티티가 저장이 되어 있지 않기 때문에 group_concat()이 동작하지 않고 서브쿼리 또한 엔티티 기반의 JPQL에서는 동작하지 않습니다. 
- **Solution** : JPQL을 사용해 해당 쿼리를 조회하려면  @ElementCollection 과 @Subselect 사용이 필요하다는 것을 깨달았지만, 불필요하게 코드가 길어질 수 있고 유지보수에 어려움이 있을수 있다고 판단하였습니다. 그래서 NativeQuery (SQL)를 사용했지만,  컴파일 시점에 오류를 찾을수 없는 단점이 있기 때문에 추후에 QueryDsl 도입을 고려중 입니다.
  
</div>
</details>



<details>
<summary>  페이징 기능 구현시 SQL문 문법 오류 </summary>
<div markdown="1">

- **Issue** : 아래의 페이징 쿼리 실행시 "Could not locate named parameter [size]" 오류 발생
    
- **Problem** : @Query(Native SQL)로 쿼리문 작성시 마지막에 입력받은 size 변수를 매핑하는 과정에서 세미콜론(;)으로 인해 오류가 발생하였습니다.
```sql
    @Query(value="select u.idx writerIdx,\n" +
            "       u.nick_name writerNickName,\n" +
            "       u.image image,\n" +
            "       p.idx postIdx,\n" +
            "       p.content postContent,\n" +
            "       case when timestampdiff(second , p.updated_at, current_timestamp) <60\n" +
            "           then concat(timestampdiff(second, p.updated_at, current_timestamp),'초 전')\n" +
            "\n" +
            "           when timestampdiff(minute , p.updated_at, current_timestamp) <60\n" +
            "           then concat(timestampdiff(minute, p.updated_at, current_timestamp),'분 전')\n" +
            "\n" +
            "           when timestampdiff(hour , p.updated_at, current_timestamp) <24\n" +
            "           then concat(timestampdiff(hour, p.updated_at, current_timestamp),'시간 전')\n" +
            "\n" +
            "           when timestampdiff(day , p.updated_at, current_timestamp) < 30\n" +
            "           then concat(timestampdiff(day, p.updated_at, current_timestamp),'일 전')\n" +
            "\n" +
            "           when timestampdiff(month , p.updated_at, current_timestamp) < 12\n" +
            "           then concat(timestampdiff(month, p.updated_at, current_timestamp),'개월 전')\n" +
            "\n" +
            "           else concat(timestampdiff(year , p.updated_at, current_timestamp), '년 전')\n" +
            "       end postCreatedDate,\n" +
            "       group_concat(pi.idx) postImageIdx,\n" +
            "       group_concat(pi.image) postimage,\n" +
            "       group_concat(pi.image_number) postImageNumber\n" +
            "\n" +
            "from (select idx, content, updated_at ,user_idx from post where status ='ACTIVE') p\n" +
            "    join (select idx, image,image_number, post_idx from post_image where status ='ACTIVE') pi\n" +
            "    on p.idx = pi.post_idx\n" +
            "    join (select idx, nick_name, image from user where status ='ACTIVE') u\n" +
            "    on p.user_idx = u.idx\n" +
            "group by p.idx having p.idx < :pageIndex\n" +
            "order by p.idx DESC limit :size;", nativeQuery = true)   //size 바로 뒤의 세미콜론으로 인해 쿼리문 오류발생
    List<GetPostsRes> getPosts(@Param("pageIndex") Long pageIndex, @Param("size") int size);
```        
- **Solution** : 세미콜론(;)을 제거하면 해결이 되지만, jpa에서 Pageable 인터페이스를 지원해 주기 때문에 이를 활용해 페이징 기능을 구현하였습니다. (쿼리문 마지막에 limit offset(pageIndex*size), size 형식으로 pageIndex와 size가 자동 매핑됩니다.)
```sql
    @Query(value="select u.idx writerIdx,\n" +
            "       u.nick_name writerNickName,\n" +
            "       u.image writerImage,\n" +
            "       p.idx postIdx,\n" +
            "       p.content postContent,\n" +
            "       case when timestampdiff(second , p.updated_at, current_timestamp) <60\n" +
            "           then concat(timestampdiff(second, p.updated_at, current_timestamp),'초 전')\n" +
            "\n" +
            "           when timestampdiff(minute , p.updated_at, current_timestamp) <60\n" +
            "           then concat(timestampdiff(minute, p.updated_at, current_timestamp),'분 전')\n" +
            "\n" +
            "           when timestampdiff(hour , p.updated_at, current_timestamp) <24\n" +
            "           then concat(timestampdiff(hour, p.updated_at, current_timestamp),'시간 전')\n" +
            "\n" +
            "           when timestampdiff(day , p.updated_at, current_timestamp) < 30\n" +
            "           then concat(timestampdiff(day, p.updated_at, current_timestamp),'일 전')\n" +
            "\n" +
            "           when timestampdiff(month , p.updated_at, current_timestamp) < 12\n" +
            "           then concat(timestampdiff(month, p.updated_at, current_timestamp),'개월 전')\n" +
            "\n" +
            "           else concat(timestampdiff(year , p.updated_at, current_timestamp), '년 전')\n" +
            "       end postCreatedDate,\n" +
            "       group_concat(pi.idx) postImageIdx,\n" +
            "       group_concat(pi.image) postimage,\n" +
            "       group_concat(pi.image_number) postImageNumber,\n" +
            "       CONCAT(IFNULL(FORMAT(pl.postLikeCount,0),0),'개') as postLikeCount,\n" +
            "       CONCAT(IFNULL(FORMAT(c.commentCount,0),0),'개') as commentCount,\n" +
            "       IFNULL(pl2.likeClickStatus,'INACTIVE') as likeClickStatus\n" +
            "\n" +
            "from (select idx, content, updated_at ,user_idx from post where status ='ACTIVE') p\n" +
            "    left join (select idx, image,image_number, post_idx from post_image where status ='ACTIVE') pi\n" +
            "    on p.idx = pi.post_idx\n" +
            "    join (select idx, nick_name, image from user where status ='ACTIVE') u\n" +
            "    on p.user_idx = u.idx\n" +
            "    left join (select post_idx, count(post_idx) as postLikeCount from post_like where status = 'ACTIVE' group by post_idx) pl\n" +
            "    on p.idx = pl.post_idx\n" +
            "    left join(select post_idx, count(post_idx) as commentCount from comment where status='ACTIVE' group by post_idx) c\n" +
            "    on p.idx = c.post_idx\n" +
            "    left join (select post_idx, 'ACTIVE' as likeClickStatus from post_like where user_idx = :userIdx) pl2\n" +
            "    on p.idx = pl2.post_idx\n" +
            "group by p.idx\n" +
            "order by p.idx DESC", nativeQuery = true)
    List<GetPostsRes> getPosts(Pageable pageable, @Param("userIdx") Long userIdx);
```    
</div>
</details>



</br>

## ❕ 회고 / 느낀점
- 외주를 할수 있는 역량이 되는지 실력을 확인함과 동시에 CTO님께 피드백을 받아볼수 있는 좋은 기회여서 이 챌린지에 참여하게 되었습니다.
- 개발가이드와 화면설계서의 요구사항을 토대로 개발을 진행하며 기능을 하나씩 구현해 나감으로써 성취감을 느낄수 있었습니다.
- 대부분의 API에 적용되는 사용자 인가절차를 인터셉터에서 공통로직으로 처리하도록 구현한 것과 스케줄러를 통해 개인정보처리방침의 동의상태 일자를 일 단위로 확인해 가입일을 기준으로 1년마다 갱신하는 로직을 구현해본것이 기억에 남습니다.    
                
                
</br>

## 👩‍💻 리팩토링 계획
- [ ] 테스트 코드(TDD) 도입
- [ ] 프론트엔드 개발자와 협업하여 API 연결 및 이슈 처리
- [ ] Docker를 이용해 Spring Boot 애플리케이션 배포
- [ ] Response 구조의 Best Practice 연구  
- [ ] @Pathvariable로 입력받는 모든 경로 변수(idx)에 유효성 검사 적용 (ex, 입력값 필터링)
- [ ] 휴먼계정과 차단계정 관리를 위한 DB 설계와 API 구현 
- [ ] 회원탈퇴시 다수의 테이블의 레코드에서 Update 쿼리문이 동작하여 반영시간이 약 10초가 걸리는 이슈 해결 필요  
- [ ] 게시글과 댓글 신고 API 구현
- [ ] Admin 도메인 DB 설계 및 API 구현
- [ ] JPQL(@Query) 코드를 Query DSL 코드로 리팩토링  
