package com.instagram.domain.chat.dao;

import com.instagram.domain.chat.domain.Chat;
import com.instagram.domain.chat.dto.GetChatRes;
import com.instagram.domain.post.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ChatDao extends JpaRepository<Chat, Long> {

    /* 13. chatIdx 통해 Chat 엔티티 조회  */
    @Query(value="select c from Chat c where c.idx = :chatIdx and c.status = 'ACTIVE'")
    Chat findByIdx(@Param("chatIdx") Long chatIdx);



    /* 12. 채팅 내역 조회 */
    @Query(value="select c.idx as chatIdx,\n" +
            "       c.content as chatContent,\n" +
            "       #c.updated_at as createdTime,\n" +
            "       case when c.updated_at >= date_add(now(), interval -1 day)\n" +
            "            then replace(replace(DATE_FORMAT(c.updated_at, '%p %h:%i'),'AM', '오전'),'PM', '오후')\n" +
            "\n" +
            "            when c.updated_at >= date_add(now(), interval -2 day )\n" +
            "            then replace(replace(DATE_FORMAT(c.updated_at, '어제,%p %h:%i'),'AM', '오전'),'PM', '오후')\n" +
            "\n" +
            "            when c.updated_at >= date_add(now(), interval -1 week )\n" +
            "            then concat(concat(timestampdiff(day, c.updated_at, current_timestamp),'일전,'), replace(replace(DATE_FORMAT(c.updated_at, '%p %h:%i'),'AM', '오전'),'PM', '오후'))\n" +
            "\n" +
            "            else replace(replace(DATE_FORMAT(c.updated_at, '%c월 %e일,%p %h:%i'),'AM', '오전'),'PM', '오후')\n" +
            "       end createdTime,\n" +
            "\n" +
            "       c.sender_idx as senderIdx,\n" +
            "       c.receiver_idx as receiverIdx,\n" +
            "       u.image as receiverImage\n" +
            "\n" +
            "from chat c left join (select idx, image from user where status = 'ACTIVE') u\n" +
            "    on c.receiver_idx = u.idx\n" +
            "\n" +
            "where ((c.sender_idx = :senderIdx and c.receiver_idx = :receiverIdx) or (c.sender_idx = :receiverIdx and c.receiver_idx = :senderIdx)) and c.status = 'ACTIVE'\n" +
            "group by c.idx\n" +
            "order by c.idx DESC", nativeQuery = true)
    List<GetChatRes> getChats(@Param("senderIdx") Long senderIdx, @Param("receiverIdx") Long receiverIdx, Pageable pageable);

}
