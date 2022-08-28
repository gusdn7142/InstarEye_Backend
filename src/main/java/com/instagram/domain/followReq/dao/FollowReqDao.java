package com.instagram.domain.followReq.dao;


import com.instagram.domain.followReq.domain.FollowReq;
import com.instagram.domain.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface FollowReqDao extends JpaRepository<FollowReq, Long> {

    /* 23. followReqIdx 통해 FollowReq 엔티티 조회  */
    @Query(value="select fr from FollowReq fr where fr.idx = :followReqIdx and fr.status = 'ACTIVE'")
    FollowReq findByIdx(@Param("followReqIdx") Long followReqIdx);


    /* 22. ReqFollower 객체와 ReqFollowee 객체를 통해 FollowReq 엔티티 조회  */
    @Query(value="select fr from FollowReq fr where fr.reqFollower = :reqFollower and fr.reqFollowee = :reqFollowee and fr.status = 'ACTIVE'")
    FollowReq findByReqFollowerAndReqFollowee(@Param("reqFollower") User reqFollower, @Param("reqFollowee") User reqFollowee);


}
