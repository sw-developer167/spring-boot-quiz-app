package com.project.quizapp.model;

import lombok.Data;

@Data
public class QuestionWrapper { // when creating a quiz we dont want to display the right answer so we are using a wrapper class

    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;

    public QuestionWrapper(Integer id, String questionTitle, String option1, String option2, String option3, String option4) {
        this.id = id;
        this.questionTitle = questionTitle;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
    }
}
