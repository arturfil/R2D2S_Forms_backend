package com.arturofilio.artus_forms.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.arturofilio.artus_forms.enums.QuestionType;

import lombok.Data;


@Entity(name = "questions")
@Data
public class QuestionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false)
    private String content;

    @Column
    private int questionOrder;

    @Column
    private QuestionType type;

    @ManyToOne
    @JoinColumn(name = "poll_id")
    private PollEntity poll;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "question")
    private List<AnswerEntity> answers = new ArrayList<>();

}