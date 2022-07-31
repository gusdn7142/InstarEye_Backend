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

    /* 2. userIdx 통해 User 엔티티 조회  */
    @Query(value="select u from User u where u.idx = :userIdx and u.status = 'ACTIVE'")
    User findByIdx(@Param("userIdx") Long userIdx);


    /* 3. 프로필 조회 API */
    @Query(value="select new com.instagram.domain.user.dto.GetUserRes(idx, name, image, nickName, webSite, introduction) from User where idx = :idx")
    GetUserRes getUser(@Param("idx") Long userIdx);

    /* 4. 프로필 편집 API */
    //이름 변경
    @Modifying
    @Transactional
    @Query(value="update User set name = :name where idx = :userIdx and status = 'ACTIVE'\n")
    void modifyName(@Param("name") String name, @Param("userIdx") Long userIdx );

    //닉네임 변경
    @Modifying
    @Transactional
    @Query(value="update User set nickName = :nickName where idx = :userIdx and status = 'ACTIVE'\n")
    void modifyNickName(@Param("nickName") String nickName, @Param("userIdx") Long userIdx );

    //웹사이트 변경
    @Modifying
    @Transactional
    @Query(value="update User set webSite = :webSite where idx = :userIdx and status = 'ACTIVE'\n")
    void modifyWebSite(@Param("webSite") String webSite, @Param("userIdx") Long userIdx );

    //소개글  변경
    @Modifying
    @Transactional
    @Query(value="update User set introduction = :introduction where idx = :userIdx and status = 'ACTIVE'\n")
    void modifyIntroduction(@Param("introduction") String introduction, @Param("userIdx") Long userIdx );

    //계정공개 유무 변경
    @Modifying
    @Transactional
    @Query(value="update User set accountHiddenState = :accountHiddenState where idx = :userIdx and status = 'ACTIVE'\n")
    void modifyAccountHiddenState(@Param("accountHiddenState") AccountHiddenState accountHiddenState, @Param("userIdx") Long userIdx );



    /* 5. 회원 탈퇴 API */
    @Modifying
    @Transactional
    @Query(value="update User set status = 'INACTIVE' where idx = :userIdx and status = 'ACTIVE'\n")
    void deleteUser(@Param("userIdx") Long userIdx );





}
