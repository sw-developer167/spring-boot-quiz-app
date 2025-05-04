package com.project.quizapp.dao;

import com.project.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionDao extends JpaRepository<Question,Integer> {

    List<Question> findByCategoryIgnoreCase(String category); // JPA will takecare , sice category is part of table

    List<Question> findByQuestionTitleIgnoreCase(String questionTitle);

    @Query(value = "select * From question q Where q.category=:category ORDER BY RANDOM() LIMIT :numQ",nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numQ);
}
