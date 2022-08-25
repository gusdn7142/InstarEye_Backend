package com.instagram.domain.follow.domain;


import com.instagram.domain.model.DataStatus;
import com.instagram.domain.user.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter

@Entity
@DynamicInsert   //jap 메서드 동작시 null인 필드 제외
@DynamicUpdate   //jap 메서드 동작시 null인 필드 제외
@Table(name = "follow")   //엔티티와 매핑할 테이블을 지정
public class Follow {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;  //인덱스

    @ManyToOne(fetch = FetchType.LAZY)  //실무에서는 n+1 쿼리조회 문제 때문에 LAZY(지연 로딩만 사용.)
    @JoinColumn(name = "followee_idx")
    private User followee;  //팔로위 인덱스

    @ManyToOne(fetch = FetchType.LAZY)  //실무에서는 n+1 쿼리조회 문제 때문에 LAZY(지연 로딩만 사용.)
    @JoinColumn(name = "follower_idx")
    private User follower;  //팔로우 인덱스


    @Column (columnDefinition = "varchar(10) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private DataStatus status;    //데이터 상태 (INACTIVE or ACTIVE)

    @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;  //생성 시각

    @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt; //갱신 시각

    public void deleteFollow(){
        this.status = DataStatus.INACTIVE;
    }

}
