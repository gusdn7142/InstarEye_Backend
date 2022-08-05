package com.instagram.domain.follow.dao;


import com.instagram.domain.follow.domain.Follow;
import com.instagram.domain.followReq.domain.FollowReq;
import com.instagram.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface FollowDao extends JpaRepository<Follow, Long> {


    /* 24. Follower 객체와 Followee 객체를 통해 FollowReq 엔티티 조회  */
    @Query(value="select f from Follow f where f.follower = :follower and f.followee = :followee and f.status = 'ACTIVE'")
    Follow findByFollowerAndFollowee(@Param("follower") User follower, @Param("followee") User followee);


    /* 26. followIdx 통해 Follow 엔티티 조회  */
    @Query(value="select f from Follow f where f.idx = :followIdx and f.status = 'ACTIVE'")
    Follow findByIdx(@Param("followIdx") Long followIdx);


    /* 26. 팔로우 등록자 체크 */
    @Query(value="select f from Follow f where f.idx = :followIdx and f.follower = :follower and f.status = 'ACTIVE' ")
    Follow checkFollower(@Param("followIdx") Long followIdx, @Param("follower") User follower);


    /* 27. 팔로우 정보 삭제 API */
    @Modifying
    @Transactional
    @Query(value="update Follow f set f.status = 'INACTIVE' where f.idx = :followIdx and f.status = 'ACTIVE' ")
    void deletefollow(@Param("followIdx") Long followIdx);



}
