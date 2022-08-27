package com.instagram.global.error;

import lombok.Getter;




@Getter
public enum BasicResponseStatus {

    /**
     * 요청 성공
     */
    SUCCESS("SUCCESS", "NOT_ERROR", "요청 성공"),

    /**
     * 공통 (Pathvariable 변수 에러값 재정의)
     */
    REQ_ERROR_INVALID_IDX("FAIL","REQ_ERROR_INVALID_IDX","idx 형식 오류"),


    /**
     * user 도메인
     */
    REQ_ERROR_INVALID_PHONE("FAIL","REQ_ERROR_INVALID_PHONE","전화번호 형식 오류"),
    REQ_ERROR_INVALID_NAME("FAIL","REQ_ERROR_INVALID_NAME","이름 형식 오류"),
    REQ_ERROR_INVALID_PASSWORD("FAIL","REQ_ERROR_INVALID_PASSWORD","비밀번호 형식 오류"),
    REQ_ERROR_NULL_BIRTHDAY("FAIL","REQ_ERROR_NULL_BIRTHDAY","생일 미입력"),
    REQ_ERROR_INVALID_BIRTHDAY("FAIL","REQ_ERROR_INVALID_BIRTHDAY","생일 형식 오류"),
    REQ_ERROR_PAST_BIRTHDAY("FAIL","REQ_ERROR_PAST_BIRTHDAY","생일 날짜 오류"),

    REQ_ERROR_INVALID_PRIVACY_POLICY_STATUS("FAIL","REQ_ERROR_INVALID_PRIVACY_POLICY_STATUS","개인정보 처리방침 미동의 (Need : AGREE)"),
    REQ_ERROR_INVALID_NICK_NAME("FAIL","REQ_ERROR_INVALID_NICK_NAME","닉네임 형식 오류"),
    REQ_ERROR_INVALID_ACCOUNT_HIDDEN_STATE("FAIL","REQ_ERROR_INVALID_ACCOUNT_HIDDEN_STATE","계정 공개상태 형식 오류 (Need : OPEN or PRIVATE)"),
    REQ_ERROR_INVALID_USERIDX("FAIL","REQ_ERROR_INVALID_USERIDX","userIdx 형식 오류"),


    RES_ERROR_EXIST_PHONE("FAIL","RES_ERROR_EXISTS_PHONE","전화번호 중복 오류"),
    RES_ERROR_EXIST_NICK_NAME("FAIL","RES_ERROR_EXISTS_NICK_NAME","닉네임 중복 오류"),
    RES_ERROR_JOIN_CHECK("FAIL","RES_ERROR_JOIN_CHECK","가입되지 않은 유저"),
    RES_ERROR_MATCH_FAIL_PASSWORD("FAIL","RES_ERROR_MATCH_FAIL_PASSWORD_","잘못된 비밀번호"),
    RES_ERROR_NOT_EXIST_USER("FAIL","RES_ERROR_NOT_EXIST_USER","존재하지 않는 사용자 계정"),
    RES_ERROR_INVALID_PRIVACY_POLICY_STATUS("FAIL","RES_ERROR_INVALID_PRIVACY_POLICY_STATUS","개인정보 처리방침 재동의 필요"),
    RES_ERROR_VALID_PRIVACY_POLICY_STATUS("FAIL","RES_ERROR_VALID_PRIVACY_POLICY_STATUS","개인정보 처리방침에 이미 동의한 상태"),
    DATABASE_ERROR_FAIL_AGREE_PRIVACY_POLICY_STATUS("FAIL", "DATABASE_ERROR_FAIL_AGREE_PRIVACY_POLICY_STATUS", "개인정보 처리방침 재동의 실패"),


    DATABASE_ERROR_CREATE_USER("FAIL", "DATABASE_ERROR_CREATE_USER", "DB에 사용자 등록 실패"),
    RES_ERROR_LOGIN_USER("FAIL", "RES_ERROR_LOGIN_USER", "로그인 실패"),

    DATABASE_ERROR_GET_USER("FAIL", "DATABASE_ERROR_GET_USER", "DB에서 사용자 정보 조회 실패"),
    DATABASE_ERROR_DELETE_USER("FAIL", "DATABASE_ERROR_DELETE_USER", "회원 탈퇴 실패"),


    /**
     *  Oauth 관련
     */
    RES_ERROR_NOT_EXIST_KAKAO_USER("FAIL","RES_ERROR_NOT_EXIST_KAKAO_USER","존재하지 않는 카카오 계정 (카카오 회원가입 필요)"),
    RES_ERROR_EXIST_KAKAO_USER("FAIL","RES_ERROR_EXIST_KAKAO_USER","이미 존재하는 카카오 계정"),
    REQ_ERROR_INVALID_KAKAOID("FAIL","REQ_ERROR_INVALID_KAKAOID","카카오 ID 입력 필요"),


    /**
     * post 도메인
     */
    REQ_ERROR_INVALID_POSTS_CONTENT("FAIL","REQ_ERROR_INVALID_POSTS_CONTENT","게시글 내용 형식 오류 (최대 1000자 입력 가능)"),
    REQ_ERROR_INVALID_POSTS_IMAGE_NUMBER("FAIL","REQ_ERROR_INVALID_POSTS_IMAGE_NUMBER","이미지 번호 형식 오류 (최대 10개 입력 가능)"),
    REQ_ERROR_INVALID_POSTS_IMAGE_FILE("FAIL","REQ_ERROR_INVALID_POSTS_IMAGE_FILE","이미지 파일 입력 오류 (최대 10개 입력 가능)"),
    REQ_ERROR_INVALID_POSTS_IMAGE_FILE_EXTENSION("FAIL","REQ_ERROR_INVALID_POSTS_IMAGE_FILE_EXTENSION","이미지 파일 형식 오류 (허용 확장자 : .gif, .jpg, .jpeg, .png)"),
    REQ_ERROR_INVALID_POSTS_IMAGE_FILE_SIZE("FAIL","REQ_ERROR_INVALID_POSTS_IMAGE_FILE_SIZE","이미지 파일 크기 오류 (최대 10MB 까지 업로드 가능)"),
    REQ_ERROR_DIFFERENT_SIZE_IMAGE_FILE_AND_IMAGE_NUMBER("FAIL","REQ_ERROR_DIFFERENT_SIZE_IMAGE_FILE_AND_IMAGE_NUMBER","이미지 파일과 이미지 번호 수 불일치"),
    REQ_ERROR_INVALID_POSTIDX("FAIL","REQ_ERROR_INVALID_POSTIDX","postIdx 형식 오류"),
    RES_ERROR_POSTS_NOT_SAME_POST("FAIL","RES_ERROR_POSTS_NOT_SAME_POST","개시글 작성자 불일치 오류"),
    REQ_ERROR_NOT_INPUT_POSTS_IMAGE_FILE("FAIL","REQ_ERROR_NOT_INPUT_POSTS_IMAGE_FILE","이미지 파일 미입력 오류"),
    REQ_ERROR_NOT_INPUT_POSTS_IMAGE_NUMBER("FAIL","REQ_ERROR_NOT_INPUT_POSTS_IMAGE_NUMBER","이미지 번호 미입력 오류"),
    REQ_ERROR_NOT_INPUT_POSTS("FAIL","REQ_ERROR_NOT_INPUT_POSTS","게시글 내용, 이미지, 이미지 번호 모두 미입력 오류"),



    RES_ERROR_POSTS_DELETE_POST("FAIL","RES_ERROR_POSTS_DELETE_POST","삭제된 게시글"),


    DATABASE_ERROR_FAIL_GET_POSTS("FAIL","DATABASE_ERROR_FAIL_GET_POSTS","DB에서 게시글 조회 실패"),
    DATABASE_ERROR_DELETE_POSTS("FAIL","DATABASE_ERROR_DELETE_POSTS","DB에서 게시글 삭제 실패"),
    DATABASE_ERROR_CREATE_POST("FAIL","DATABASE_ERROR_CREATE_POST","DB에 게시글 등록 실패"),
    DATABASE_ERROR_MODIFY_FAIL_POSTS_CONTENT("FAIL", "DATABASE_ERROR_MODIFY_FAIL_POSTS_CONTENT", "게시글 내용 변경 오류"),
    DATABASE_ERROR_MODIFY_FAIL_POSTS_IMAGE("FAIL", "DATABASE_ERROR_MODIFY_FAIL_POSTS_IMAGE", "게시글 이미지 변경 오류"),
    //DATABASE_ERROR_MODIFY_POSTS("FAIL","DATABASE_ERROR_MODIFY_POSTS","DB에서 게시글 수정 실패"),

    /**
     * chat 도메인
     */
    REQ_ERROR_INVALID_CHATS_CONTENT("FAIL","REQ_ERROR_INVALID_CHATS_CONTENT","채팅 내용 형식 오류 (최대 200자 입력 가능)"),
    RES_ERROR_CHATS_FAIL_DELETE_MESSAGE("FAIL","RES_ERROR_CHATS_FAIL_DELETE_MESSAGE","삭제된 채팅 메시지"),
    REQ_ERROR_INVALID_SENDERIDX("FAIL","REQ_ERROR_INVALID_SENDERIDX","senderIdx 형식 오류"),
    REQ_ERROR_CHATS_SAME_SENDERIDX_RECEIVERIDX("FAIL","REQ_ERROR_CHATS_SAME_SENDERIDX_RECEIVERIDX","senderIdx와 receiverIdx 값 동일 오류"),


    DATABASE_ERROR_CREATE_CHATS("FAIL","DATABASE_ERROR_CREATE_CHATS","채팅 메시지 전송 실패"),
    DATABASE_ERROR_FAIL_GET_CHATS("FAIL","DATABASE_ERROR_FAIL_GET_CHATS","DB에서 채팅내역 조회 실패"),
    DATABASE_ERROR_FAIL_DELETE_CHATS_MESSAGE("FAIL","DATABASE_ERROR_FAIL_DELETE_CHATS_MESSAGE","DB에서 채팅 메시지 삭제 실패"),



    /**
     * comment 도메인
     */
    REQ_ERROR_INVALID_COMMENTS_CONTENT("FAIL","REQ_ERROR_INVALID_COMMENTS_CONTENT","댓글 내용 형식 오류 (최대 200자 입력 가능)"),
    RES_ERROR_COMMENTS_NOT_SAME_COMMENTER("FAIL","RES_ERROR_COMMENTS_NOT_SAME_COMMENTER","댓글 작성자 불일치 오류"),
    RES_ERROR_COMMENTS_DELETE_COMMENT("FAIL","RES_ERROR_COMMENTS_DELETE_COMMENT","삭제된 댓글"),

    DATABASE_ERROR_CREATE_COMMENTS("FAIL","DATABASE_ERROR_CREATE_COMMENTS","DB에 댓글 정보 등록 실패"),
    DATABASE_ERROR_FAIL_GET_COMMENTS("FAIL","DATABASE_ERROR_FAIL_GET_COMMENTS","DB에서 댓글 조회 실패"),
    DATABASE_ERROR_FAIL_DELETE_COMMENTS("FAIL","DATABASE_ERROR_FAIL_DELETE_COMMENTS","DB에서 댓글 삭제 실패"),
    DATABASE_ERROR_MODIFY_FAIL_COMMENTS("FAIL", "DATABASE_ERROR_MODIFY_FAIL_COMMENTS", "DB에서 댓글 내용 변경 실패"),


    /**
     * postLike 도메인
     */
    RES_ERROR_POSTLIKES_EXIST_LIKE("FAIL", "RES_ERROR_POSTLIKES_EXIST_LIKE", "게시물 좋아요 중복 오류"),
    RES_ERROR_POSTLIKES_DELETE_LIKE("FAIL","RES_ERROR_POSTLIKES_DELETE_LIKE","이미 취소된 게시글 좋아요"),
    RES_ERROR_POSTLIKES_NOT_SAME_LIKER("FAIL","RES_ERROR_POSTLIKES_NOT_SAME_LIKER","개시글 좋아요 등록자 불일치 오류"),
    DATABASE_ERROR_CREATE_POSTLIKES("FAIL","DATABASE_ERROR_CREATE_POSTLIKES","DB에 게시글 좋아요 등록 실패"),
    DATABASE_ERROR_FAIL_DELETE_POSTLIKES("FAIL","DATABASE_ERROR_FAIL_DELETE_POSTLIKES","DB에서 게시글 좋아요 취소 실패"),


    /**
     * commentLike 도메인
     */
    RES_ERROR_COMMENTLIKES_EXIST_LIKE("FAIL", "RES_ERROR_COMMENTLIKES_EXIST_LIKE", "댓글 좋아요 중복 오류"),
    RES_ERROR_COMMENTLIKES_DELETE_LIKE("FAIL","RES_ERROR_COMMENTLIKES_DELETE_LIKE","이미 취소된 댓글 좋아요"),
    RES_ERROR_COMMENTLIKES_NOT_SAME_LIKER("FAIL","RES_ERROR_COMMENTLIKES_NOT_SAME_LIKER","댓글 좋아요 등록자 불일치 오류"),
    DATABASE_ERROR_CREATE_COMMENTLIKES("FAIL","DATABASE_ERROR_CREATE_COMMENTLIKES","DB에 댓글 좋아요 등록 실패"),
    DATABASE_ERROR_FAIL_DELETE_COMMENTLIKES("FAIL","DATABASE_ERROR_FAIL_DELETE_COMMENTLIKES","DB에서 댓글 좋아요 취소 실패"),

    /**
     * AccessToken 관련
     */
    ERROR_FAIL_ISSUE_ACCESS_TOKEN("FAIL","ERROR_FAIL_ISSUE_ACCESS_TOKEN","accessToken 발급 실패"),
    ERROR_EMPTY_ACCESS_TOKEN("FAIL", "ERROR_EMPTY_ACCESS_TOKEN", "accessToken 미입력 오류"),
    ERROR_INVALID_ACCESS_TOKEN("FAIL", "ERROR_INVALID_ACCESS_TOKEN", "accessToken 변조 혹은 만료"),
    ERROR_INVALID_USER_ACCESS_TOKEN("FAIL","ERROR_INVALID_USER_ACCESS_TOKEN","권한이 없는 유저의 접근"),


    /**
     * kakao 로그인 관련
     */
    ERROR_INVALID_KAKAO_ACCESS_TOKEN("FAIL", "ERROR_INVALID_KAKAO_ACCESS_TOKEN", "kakaoAccessToken 변조 혹은 만료"),


    /**
     * Paging 관련
     */
    REQ_ERROR_NOT_EXIST_PAGING_PAGEINDEX("FAIL","REQ_ERROR_NOT_EXIST_PAGING_PAGEINDEX","pageIndex 파라미터 미입력"),
    REQ_ERROR_NOT_EXIST_PAGING_SIZE("FAIL","REQ_ERROR_NOT_EXIST_PAGING_SIZE","size 파라미터 미입력"),


    /**
     * FollowReq 도메인
     */
    RES_ERROR_NOT_EXIST_REQFOLLOWEE("FAIL","RES_ERROR_NOT_EXIST_REQFOLLOWEE","존재하지 않는 팔로위 계정"),
    RES_ERROR_FOLLOWREQS_OPEN_ACCOUNT("FAIL", "RES_ERROR_FOLLOWREQS_OPEN_ACCOUNT", "공개된 계정이므로 팔로우 API 이용 필요"),
    RES_ERROR_FOLLOWREQS_EXIST_FOLLOW_REQ("FAIL", "RES_ERROR_FOLLOWREQS_EXIST_FOLLOW_REQ", "팔로우 요청 중복 오류"),
    DATABASE_ERROR_CREATE_FOLLOWREQS("FAIL","DATABASE_ERROR_CREATE_FOLLOWREQS","DB에 팔로우 요청 등록 실패"),
    RES_ERROR_FOLLOWREQS_DELETE_FOLLOWREQ("FAIL","RES_ERROR_FOLLOWREQS_DELETE_FOLLOWREQ","이미 취소된 팔로우 요청"),
    RES_ERROR_FOLLOWREQS_NOT_SAME_REQFOLLOWER("FAIL","RES_ERROR_FOLLOWREQS_NOT_SAME_REQFOLLOWER","팔로우 요청 등록자 불일치 오류"),
    DATABASE_ERROR_FAIL_DELETE_FOLLOWREQS("FAIL","DATABASE_ERROR_FAIL_DELETE_FOLLOWREQS","DB에서 팔로우 요청 취소 실패"),
    RES_ERROR_FOLLOWREQS_NOT_SAME_REQFOLLOWEE("FAIL","RES_ERROR_FOLLOWREQS_NOT_SAME_REQFOLLOWEE","팔로우 요청 받은 내역 없음"),
    DATABASE_ERROR_FAIL_DELETE_FOLLOWREQS_REFUSE("FAIL","DATABASE_ERROR_FAIL_DELETE_FOLLOWREQS_REFUSE","DB에서 팔로우 요청 거절 실패"),
    REQ_ERROR_INVALID_FOLLOWERREQIDX("FAIL","REQ_ERROR_INVALID_FOLLOWERREQIDX","followerReqIdx 형식 오류"),
    REQ_ERROR_FOLLOWREQ_SAME_FOLLOWERREQIDX_FOLLOWEEREQIDX("FAIL","REQ_ERROR_FOLLOWREQ_SAME_FOLLOWERREQIDX_FOLLOWEEREQIDX","followerReqIdx와 followeeReqIdx 값 동일 오류"),


    /**
     * Follow 도메인
     */
    RES_ERROR_NOT_EXIST_FOLLOWEE("FAIL","RES_ERROR_NOT_EXIST_FOLLOWEE","존재하지 않는 팔로위 계정"),
    RES_ERROR_FOLLOWS_EXIST_FOLLOW("FAIL", "RES_ERROR_FOLLOWS_EXIST_FOLLOW", "팔로우 중복 오류"),
    DATABASE_ERROR_CREATE_FOLLOWS("FAIL","DATABASE_ERROR_CREATE_FOLLOWS","DB에 팔로우 등록 실패"),
    RES_ERROR_FOLLOWS_PRIVATE_ACCOUNT("FAIL", "RES_ERROR_FOLLOWS_PRIVATE_ACCOUNT", "비공개 계정이므로 팔로우 요청과 승인 필요"),
    RES_ERROR_FOLLOWS_REQUEST_FOLLOW("FAIL", "RES_ERROR_FOLLOWS_REQUEST_FOLLOW", "팔로우 요청 필요"),
    RES_ERROR_FOLLOWS_DELETE_FOLLOW("FAIL","RES_ERROR_FOLLOWS_DELETE_FOLLOW","이미 취소된 팔로우"),
    RES_ERROR_FOLLOWS_NOT_SAME_FOLLOWER("FAIL","RES_ERROR_FOLLOWS_NOT_SAME_FOLLOWER","팔로우 등록자 불일치 오류"),
    DATABASE_ERROR_FAIL_DELETE_FOLLOWS("FAIL","DATABASE_ERROR_FAIL_DELETE_FOLLOWS","DB에서 팔로우 취소 실패"),
    REQ_ERROR_INVALID_FOLLOWERIDX("FAIL","REQ_ERROR_INVALID_FOLLOWERIDX","followerIdx 형식 오류"),
    REQ_ERROR_FOLLOWS_SAME_FOLLOWERIDX_FOLLOWEEIDX("FAIL","REQ_ERROR_FOLLOWS_SAME_FOLLOWERIDX_FOLLOWEEIDX","followerIdx와 followeeIdx 값 동일 오류"),


    /**
     *
     * AWS S3 관련
     */
    S3_ERROR_FAIL_UPLOAD_FILE("FAIL","S3_ERROR_UPLOAD_FILE","S3에 파일 업로드 실패"),
    S3_ERROR_FAIL_DELETE_FILE("FAIL","S3_ERROR_FAIL_DELETE_FILE","S3에서 파일 삭제 실패"),


    /**
     * DB 오류, 서버 오류
     */
    DATABASE_ERROR("FAIL", "DATABASE_ERROR", "DB에서 데이터 조회 실패"),
    SERVER_ERRER("FAIL", "SERVER_ERROR", "서버에서 오류 발생"),


    DATABASE_ERROR_MODIFY_FAIL_USER_NAME("FAIL", "DATABASE_ERROR_MODIFY_FAIL_USER_NAME", "이름 변경 오류"),
    DATABASE_ERROR_MODIFY_FAIL_USER_NICKNAME("FAIL", "DATABASE_ERROR_MODIFY_FAIL_USER_NICKNAME", "닉네임 변경 오류"),
    DATABASE_ERROR_MODIFY_FAIL_USER_WEBSITE("FAIL", "DATABASE_ERROR_MODIFY_FAIL_USER_WEBSITE", "웹사이트 변경 오류"),
    DATABASE_ERROR_MODIFY_FAIL_USER_INTRODUCTION("FAIL", "DATABASE_ERROR_MODIFY_FAIL_USER_INTRODUCTION", "소개글 변경 오류"),
    DATABASE_ERROR_MODIFY_FAIL_USER_ACCOUNT_HIDDEN_STATE("FAIL", "DATABASE_ERROR_MODIFY_FAIL_USER_ACCOUNT_HIDDEN_STATE", "계정공개 유무 변경 오류");





    private String status;
    private String code;
    private String message;



    private BasicResponseStatus(String status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }


    public void setStatus(String status) {
        this.status = status;
    }
    public void setCode(String code){
        this.code = code;
    }
    public void setMessage(String message){
        this.message = message;
    }


}