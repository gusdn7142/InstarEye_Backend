package com.instagram.domain.comment.domain;


import com.instagram.domain.model.DataStatus;
import com.instagram.domain.post.domain.Post;
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
@DynamicInsert   //null인 필드 제외
@DynamicUpdate   //null인 필드 제외
@Table(name = "comment")   //엔티티와 매핑할 테이블을 지정
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;  //인덱스

    @Column(nullable=false, columnDefinition="varchar(602)")
    private String content;  //댓글 내용

    @ManyToOne(fetch = FetchType.LAZY)  //실무에서는 n+1 쿼리조회 문제 때문에 LAZY(지연 로딩만 사용.)
    @JoinColumn(name = "post_idx")
    private Post post;  //게시글 인덱스

    @ManyToOne(fetch = FetchType.LAZY)  //실무에서는 n+1 쿼리조회 문제 때문에 LAZY(지연 로딩만 사용.)
    @JoinColumn(name = "user_idx")
    private User user;  //댓쓴이

    @Column (columnDefinition = "varchar(10) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private DataStatus status;    //데이터 상태 (INACTIVE or ACTIVE)

    @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime createdAt;  //생성 시각

    @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updatedAt; //갱신 시각

    public void deleteComment(){
        this.status = DataStatus.INACTIVE;
    }

    public void updateContent(String content){
        this.content = content;
    }

}
