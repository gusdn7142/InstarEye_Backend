package com.instagram.domain.user.dao;


import com.instagram.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface UserDao extends JpaRepository<User, Long> {


    /* 1. 전화번호를 통해 User 엔티티 조회  */
    @Query(value="select u from User u where u.phone = :phone and u.status = 'ACTIVE'")
    User findByPhone(@Param("phone") String phone);

    /* 2. 닉네임를 통해 User 엔티티 조회  */
    @Query(value="select u from User u where u.nickName = :nickName and u.status = 'ACTIVE'")
    User findByNickName(@Param("nickName") String nickName);









}
