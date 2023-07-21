
## 📝 프로젝트 개요
> [인스타그램](https://www.instagram.com/)을 모티브로 하여 만든 Rest API 서버입니다.
- 목적 : 지인, 가족, 친구들간에 사진과 글을 공유하는 서비스 개발  
- 기간 : 2022년 7월 25일 ~ 2023년 4월 3일 
- 팀 구성 : 백엔드 1명

</br>
<!--  -->

## 💁‍♂️ Wiki  (보류)
- ✍ [프로젝트 전체 업무 관리](https://fir-lancer-6bb.notion.site/b3c9db9b528e4c1880a6d398ce62e023?pvs=4)
- 📰 [API 명세서](https://fir-lancer-6bb.notion.site/API-1d94156d9f984832ba21b023aa5716f1)

</br>

## 🛠 사용 기술
![tech stack](https://github.com/gusdn7142/ReadMe_Collection/assets/62496215/9dbcd705-5801-47ff-81b1-bf39ac10dee3)

</br>

## 📦 ERD와 엔티티 설계
### 1. ERD 설계
![Instagram_ERD](https://user-images.githubusercontent.com/62496215/183288506-76da300b-f533-4cfd-ae43-70c8a07cbfbf.png)  
<details>
  <summary> ERD는 다음과 같은 기준으로 설계하였습니다. </summary>
  <div markdown="1">

  - **공통 설계 기준**  
    1. 소문자 사용  
    2. 단어 축약 X : password (O), pwd (X)  
    3. 동사는 능동태 사용 : create_at(O), created_at(X)  
  - **테이블 설계 기준**
    1. 단수형 사용
    2. 테이블 명에 snake case(_) 사용 
  - **칼럼 설계 기준**
    1. 칼럼 명에 snake case(_) 사용
    2. password 칼럼 : 암호화된 패스워드 문자열을 저장하므로 VARCHAR(200)으로 지정
    3. image 칼럼 : 게시글 이미지에 대한 URL을 TEXT 타입으로 저장
    4. status 칼럼 : JPA Entity 클래스의 Enem 타입 status 상수와 매핑하기 위해 VARCHAR 타입 사용
  - **MySQL 데이터 타입 지정 기준**
    1. BIGINT 타입 : 많은 사용자를 감당하기 위해 idx 칼럼에 지정
    2. VARCHAR 타입 : 영문 1바이트, 한글 3바이트임을 고려하여 각 칼럼에 바이트 할당
    3. TIMSTAMP 타입 : 테이블에서 하나의 row의 값들이 생성(created_at)되고 변경(updated_at)될때마다 시각을 기록하기 위해 사용하며, JPA Entity 클래스의 LocalDateTime 타입 필드와 매핑
    
  </div>
</details>

### 2. 엔티티 설계
<details>
  <summary> 게시글(Post) 엔티티 설계 </summary>
  <div markdown="1">

  ```java
  @Entity  
  @Table(name = "post")  
  public class Post {
  
      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private Long idx;  
  
      @Column (nullable=false, columnDefinition="varchar(3002)")
      private String content;
  
      @ManyToOne(fetch = FetchType.LAZY)  
      @JoinColumn(name = "user_idx")
      private User user;  
  
      @Column (nullable=true, columnDefinition ="varchar(10) default 'VISIBLE'")
      @Enumerated(EnumType.STRING)
      private PostStatus postStatus;  
  
      @Column (columnDefinition = "varchar(10) default 'ACTIVE'")
      @Enumerated(EnumType.STRING)
      private DataStatus status;   
  
      @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP")
      private LocalDateTime createdAt;  
  
      @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
      private LocalDateTime updatedAt; 
  
      @OneToMany(mappedBy = "post")
      private List<PostImage> postImages = new ArrayList<>();
  }
   ```
  </div>
</details

- 각 엔티티의 상세 설계 내용은 [여기](https://fir-lancer-6bb.notion.site/ERD-JPA-e7c2ab35454745cc87f6388da33f1c3b#bc7c2c195fc04a32982b3507d9cf6bdf)를 참고해 주시면 감사합니다.  
  
</br>

## 🔩 시스템 구성도
### 1. 전체 서비스 구조  
![Architecture](https://github.com/gusdn7142/ReadMe_Collection/assets/62496215/4f531e5c-bae5-4357-a3cd-b8b957c8ae98)

### 2. 서버 동작 흐름 
![Server Flow](https://github.com/gusdn7142/ReadMe_Collection/assets/62496215/1fc3c429-3374-41fa-b13b-9f122715b28c)
- 회원가입과 게시글 조회 API 호출시의 서버 동작 흐름입니다.
- 회원가입 API 동작 흐름  
  - Client <-> UserController <-> UserService <-> UserRepository <-> MySQL DB
- 게시글 조회 API 동작 흐름  
  - Client <-> Interceptor <-> Local Cache <-> PostController <-> PostService <-> PostRepository <-> MySQL DB 
- 기타 API의 백엔드 로직 흐름은 [시퀀스 다이어그램](https://fir-lancer-6bb.notion.site/d1ae0524506f4f4780495b73679bc597?pvs=4)을 참고해 주시면 감사합니다. 

### 3. 디렉터리 구조
```bash
📂 src
 └── 📂 main         
      ├── 📂 java.com.instagram          			
      |    ├── 📂 domain            		  #도메인 관리
      |    |    ├── 📂 comment            
      |    |    ├── 📂 commentLike
      |    |    ├── 📂 follow
      |    |    ├── 📂 followReq
      |    |    ├── 📂 user
      |    |    ├── 📂 postLike
      |    |    ├── 📂 post                    #게시글 도메인
      |    |    |    ├── 📂 controller           #컨트롤러 계층
      |    |    |    ├── 📂 service              #서비스 계층
      |    |    |    ├── 📂 dao                  #레포지토리 계층
      |    |    |    ├── 📂 dto                  #데이터 전송 
      |    |    |    └── 📂 domain               #엔티티 관리 
      |    |    └── 📂 model                   #공통으로 사용되는 ENUM 클래스 정의
      |    ├── 📂 global                     #전체적인 설정 관리
      |    |    ├── 📂 config                  #환경설정 관리 (인터셉터, 시큐리티, 스웨거)
      |    |    ├── 📂 error                   #예외 처리 클래스 관리
      |    |    ├── 📂 interceptor             #인터셉터 관리
      |    |    ├── 📂 util                    #유틸 클래스 관리
      |    |    ├── 📂 Scheduler               #스케줄러 관련
      |    |    └── 📂 Security                #보안 관련
      |    ├── 📂 infra                      #외부 인프라스트럭처 관리
      |    |    └── 📂 aws                     #AWS 서비스 관리
      |    └── 📄 InstagramApplication.java  #애플리케이션 실행 클래스
      └── 📂 resources
           └── 📄 applicaiton.yml            #DB, 외부서비스, 로그 등의 연결 설정
📄 .gitignore                                #깃허브 업로드시 제외 파일 관리  
📄 build.gradle                                                                   
📄 README.md
``` 
- 도메인형으로 패키지 구조를 설계했습니다.
- 디렉터리별 세부 파일 구조는 [Wiki](https://github.com/gusdn7142/Instagram_Clone_Server/wiki/%F0%9F%93%81-Directory-Structure)를 참고해 주시면 감사합니다.



</br>

## 👨🏻‍🏫 프로젝트 주요 관심사

####  1인 백엔드 개발로 설계부터 기능 구현까지 주도적으로 진행
- [개발환경 구축](https://fir-lancer-6bb.notion.site/11b2576fc1e348eaac5ef508ff5f8e1f?pvs=4)

#### 사용자별 게시글 관리 Rest API 서버 설계 및 개발 & 개선
- [ERD와 JPA 엔티티 설계](https://fir-lancer-6bb.notion.site/ERD-JPA-e7c2ab35454745cc87f6388da33f1c3b?pvs=4)
- [Rest API 설계](https://fir-lancer-6bb.notion.site/Rest-API-bfedc966e885459da0a600256dc59e75?pvs=4)
- [도메인형으로 패키지 구조 설계](https://fir-lancer-6bb.notion.site/132ff3e479994de9a89685a716d3e33d?pvs=4)
- [단방향 암호화로 패스워드 외부 노출 방지](https://fir-lancer-6bb.notion.site/d95eacc9e93f49f986b25f631d0e1e0b?pvs=4)
- [룰 기반 파일 업로드 실행](https://fir-lancer-6bb.notion.site/ea82b12388cd4f778b2de9865ef00d6f?pvs=4)
- [페이징을 통해 사용자 경험 개선](https://fir-lancer-6bb.notion.site/080672fffcb6411d8a109617b7c98632?pvs=4)
- [네이티브 SQL을 JPQL로 리팩토링하여 쿼리 실행시간 5.82% 개선](https://fir-lancer-6bb.notion.site/SQL-JPQL-5-82-f772f7c588014901b9dc411e8b04ba97?pvs=4)
- [캐시 도입으로 응답시간 45.78% 개선](https://fir-lancer-6bb.notion.site/32-84-963baf0d6a6449d1b8814b834acd4308?pvs=4)
- [슬로우 쿼리를 JPA 메서드로 리팩토링하여 쿼리 실행 시간 96.91% 개선](https://fir-lancer-6bb.notion.site/JPA-96-91-c7534e8b5e87495295290f69a65bfd84?pvs=4)

#### 인터셉터를 이용해 API 요청에 대한 사용자 인증 및 인가 로직 구현
- [인터셉터에서 JWT 토큰으로 API 접근을 검증 후 허가되지 않은 사용자 접근 차단](https://fir-lancer-6bb.notion.site/JWT-API-164759876b8042eba10e4835fe3fe987?pvs=4)  

#### 스케줄러를 이용해 개인정보 수집 동의를 1년 주기로 만료 및 갱신
- [스프링 스케줄러로 매일 자정에 작업을 예약하여 개인정보 수집 동의 시기가 1년이 지난 사용자의 로그인 제한](https://fir-lancer-6bb.notion.site/1-29b752ee20bc45228f68fe3227ffaf81?pvs=4)

#### 예외 처리 프로세스 설계 및 구현
- [시퀀스 다이어그램으로 백엔드 로직을 시각화해 단계별로 필요한 예외 처리 로직 설계](https://fir-lancer-6bb.notion.site/d1ae0524506f4f4780495b73679bc597?pvs=4)
- [예외 상황별 에러 코드와 에러 메시지 로직 구현](https://fir-lancer-6bb.notion.site/776cc2d85efb4880a1cf5b5f8b33b689?pvs=4)

#### 트랜잭션 롤백 프로세스 설계 및 구현
- [트랜잭션 실패 시의 롤백 프로세스 설계 및 구현](https://fir-lancer-6bb.notion.site/fc6074bc4ff44834b7a022cb04a9a6f8?pvs=4)

#### 인프라 구축 및 고도화
- [웹 서버와 DB 서버 구축, 도메인과 HTTPS 적용](https://fir-lancer-6bb.notion.site/DB-HTTPS-c068bdd0b4534b05b4e6f94e6537cb0a?pvs=4)
- [도커 이미지로 AWS EC2에 API 배포](https://fir-lancer-6bb.notion.site/AWS-EC2-API-a05588b829af48b698a270257a537ce8?pvs=4)

#### 체계적인 업무 프로세스 정립
- [노션으로 일자별 프로젝트 업무와 이슈를 관리](https://fir-lancer-6bb.notion.site/b3c9db9b528e4c1880a6d398ce62e023?pvs=4)
- [노션으로 작성한 API 명세서를 Swagger로 자동화](https://fir-lancer-6bb.notion.site/API-Swagger-064802576f504d11a00cf0a19611957a?pvs=4)




</br>

## 💡 서버 실행시 주의사항 (==개발 환경 정보..  배포 주소도 넣을까?)

### 환경변수 설정
applicaiton.yml 파일에 애플리케이션 정보, DB 정보, AWS S3 정보를 기입해 주시면 됩니다.
  
```
# Application
server:
  port: 10000

# MYSQL DB
spring:
  datasource:
    url:
    username: 
    password: 

# AWS S3
cloud:
  aws:
    credentials:
      access-key:
      secret-key:
    region:
      static: 
    s3:
      bucket: 
    stack:
      auto: 
``` 
  
### 빌드 및 실행 방법  
```
# 프로젝트 빌드 
$ ./gradlew clean build

# Jar 파일 실행
$ java -jar build/libs/Instagram_Clone_Server-0.0.1-SNAPSHOT.jar
``` 
  
### Swagger 접속 주소
  ```
  http://localhost:10000/swagger-ui/
  ```

</br>

## 👩‍💻 리팩토링 계획 (보류) 
  
