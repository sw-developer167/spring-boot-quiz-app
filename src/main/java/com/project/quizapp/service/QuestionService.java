package com.project.quizapp.service;

import com.project.quizapp.model.Question;
import com.project.quizapp.dao.QuestionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service // we can use @component as well
public class QuestionService {

    @Autowired
    QuestionDao questionDao;

    /*public List<Question> getAllQuestions() {
        return questionDao.findAll();
    }*/

    public ResponseEntity<List<Question>> getAllQuestions(){
        try {
            return new ResponseEntity<>(questionDao.findAll(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    /*public List<Question> getQuestionsByCategory(String category) {
        return questionDao.findByCategoryIgnoreCase(category);
    }*/

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategoryIgnoreCase(category),HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.BAD_REQUEST);
    }

    /*public String addQuestion(Question question) {
        questionDao.save(question);
        return "success";
    }
     */

    public ResponseEntity<String> addQuestion(Question question) {
        try{
            questionDao.save(question);
            return new ResponseEntity<>("success",HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>("Not added",HttpStatus.BAD_REQUEST);

    }

    public String deleteQuestionByTitle(String title) {
        String trimmedTitle = title.trim();
        System.out.println("🔍 Incoming title: '" + title + "'");
        System.out.println("✂️ Trimmed title: '" + trimmedTitle + "'");

        List<Question> list = questionDao.findByQuestionTitleIgnoreCase(trimmedTitle);
        System.out.println("🔎 Matched questions: " + list.size());

        if (list.isEmpty()) return "No match found.";

        questionDao.deleteAll(list);
        return list.size() + " question(s) deleted.";
    }


    public ResponseEntity<String> updateQuestion(int id, Question updatedQuestion) {
        Optional<Question> optionalQuestion = questionDao.findById(id); // A container object which may or may not contain a Question object.It helps you safely handle null values without risking NullPointerException.

        // If no Question is found (Optional is empty), return a 404 Not Found response.
        if (optionalQuestion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("❌ Question not found with ID: " + id);
        }


        // If the question exists, retrieve the actual Question object from the Optional.
        Question existing = optionalQuestion.get();

        // Update each field of the existing question with values from the updatedQuestion object.
        existing.setQuestionTitle(updatedQuestion.getQuestionTitle());
        existing.setOption1(updatedQuestion.getOption1());
        existing.setOption2(updatedQuestion.getOption2());
        existing.setOption3(updatedQuestion.getOption3());
        existing.setOption4(updatedQuestion.getOption4());
        existing.setRightAnswer(updatedQuestion.getRightAnswer());
        existing.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
        existing.setCategory(updatedQuestion.getCategory());

        // Save the updated Question object back to the database.
        questionDao.save(existing);

        return ResponseEntity.ok("✅ Question updated successfully for ID: " + id);
    }
}
