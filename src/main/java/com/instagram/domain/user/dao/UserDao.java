package com.instagram.domain.user.dao;


import com.instagram.domain.user.domain.AccountHiddenState;
import com.instagram.domain.user.domain.User;
import com.instagram.domain.user.dto.GetUserRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface UserDao extends JpaRepository<User, Long> {


    /* 1. 전화번호를 통해 User 엔티티 조회  */
    @Query(value="select u from User u where u.phone = :phone and u.status = 'ACTIVE'")
    User findByPhone(@Param("phone") String phone);

    /* 2. 닉네임를 통해 User 엔티티 조회  */
    @Query(value="select u from User u where u.nickName = :nickName and u.status = 'ACTIVE'")
    User findByNickName(@Param("nickName") String nickName);

    /* 비밀번호를 통해 User 엔티티 조회  */
    @Query(value="select u from User u where u.password = :password and u.status = 'ACTIVE'")
    User findByPassword(@Param("password") String password);

    /* 2. userIdx 통해 User 엔티티 조회  */
    @Query(value="select u from User u where u.idx = :userIdx and u.status = 'ACTIVE'")
    User findByIdx(@Param("userIdx") Long userIdx);


    /* 3. 프로필 조회 API */
    @Query(value="select u.idx as userIdx,\n" +
            "       u.name as userName,\n" +
            "       u.image as userImage,\n" +
            "       u.nick_name as userNickName,\n" +
            "       u.web_site as userWebSite,\n" +
            "       u.introduction as userIntroduction,\n" +
            "       IFNULL(FORMAT(p.postCount,0),0) as postCount,\n" +
            "       IFNULL(FORMAT(fwr.followerCount,0),0) as followerCount,\n" +
            "       IFNULL(FORMAT(fwe.followeeCount,0),0) as followeeCount,\n" +
            "       group_concat(p2.idx) as postIdx,\n" +
            "       group_concat(pi.image) as postImage\n" +
            "\n" +
            "from user u\n" +
            "left join (select user_idx, count(user_idx) as postCount from post where status = 'ACTIVE' group by user_idx) p\n" +
            "    on u.idx = p.user_idx\n" +
            "left join (select follower_idx, count(follower_idx) as followerCount from follow where status = 'ACTIVE' group by follower_idx) fwr\n" +
            "    on u.idx = fwr.follower_idx\n" +
            "left join (select followee_idx, count(followee_idx) as followeeCount from follow where status = 'ACTIVE' group by followee_idx) fwe\n" +
            "    on u.idx = fwe.followee_idx\n" +
            "left join (select idx, user_idx from post where status = 'ACTIVE' group by idx) p2\n" +
            "    on u.idx = p2.user_idx\n" +
            "left join (select image, post_idx from post_image where status ='ACTIVE' and image_number = 1 order by idx DESC) pi\n" +
            "     on p2.idx = pi.post_idx\n" +
            "\n" +
            "where  u.idx = :userIdx and u.status ='ACTIVE'",nativeQuery = true)
    GetUserRes getUser(@Param("userIdx") Long userIdx);


    /* 개인정보 처리방침 동의 상태 1년마다 갱신 (스케줄러) */
    @Modifying
    @Transactional
    @Query(value="update user u set u.privacy_policy_status='DISAGREE' where (date_add(u.created_at, interval +1 year) < now())", nativeQuery = true)
    void modifyPrivacyPolicyStatus();


    /* 개인정보 처리방침 재동의 API */
    @Modifying
    @Transactional
    @Query(value="update User u set u.privacyPolicyStatus='AGREE' where u.privacyPolicyStatus = 'DISAGREE' and u.status = 'ACTIVE' and u.idx = :userIdx")
    void reagreePrivacyPolicy(@Param("userIdx") Long userIdx );



    /* createdAt에 1년 추가 (스케줄러 동작에 맞추기 위함) */
    @Modifying
    @Transactional
    @Query(value="update user u set u.created_at= (date_add(u.created_at, interval +1 year)) where u.status = 'ACTIVE' and u.idx = :userIdx", nativeQuery = true)
    void addOneYeartoCreatedAt(@Param("userIdx") Long userIdx );



}
