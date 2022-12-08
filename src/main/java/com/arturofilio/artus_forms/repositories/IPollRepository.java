package com.arturofilio.artus_forms.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.arturofilio.artus_forms.entities.PollEntity;
import com.arturofilio.artus_forms.interfaces.IPollResult;

@Repository
public interface IPollRepository extends CrudRepository<PollEntity, Long> {
    PollEntity findByPollId(String pollId);
    PollEntity findById(long id);
    Page<PollEntity> findAllByUserId(long userId, Pageable pageable);
    PollEntity findByPollIdAndUserId(String pollId, long userId);
    @Query(value = 
        " SELECT q.question_order questionOrder, prd.question_id AS questionId, q.content question, prd.answer_id AS answerId, a.content answer, count(prd.answer_id) as result" +
        " FROM poll_replies pr" + 
        " LEFT JOIN poll_reply_details prd ON prd.poll_reply_id = pr.id" +
        " LEFT JOIN answers a ON a.id = prd.answer_id" +
        " LEFT JOIN questions q ON q.id = prd.question_id" +
        " WHERE pr.poll_id = :pollId" +
        " GROUP BY prd.question_id, prd.answer_id" +
        " ORDER BY question_order ASC",
        nativeQuery = true
    )
    List<IPollResult> getPollResults(@Param("pollId") long pollId);
}
