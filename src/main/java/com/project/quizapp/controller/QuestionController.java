package com.project.quizapp.controller;

import com.project.quizapp.model.Question;
import com.project.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @GetMapping("allQuestions") // fetch the data from server
    public ResponseEntity<List<Question>> getAllQuestions(){ //public <List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){ // since we will be passing value in the address , so we need @PathVariable and we can also give @PathVarible("name") if we are using different names
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add") // post data to server
    public ResponseEntity<String> addQuestion(@RequestBody Question question){ // we get question from body, we get JSON format and will be taken care by spring
        return questionService.addQuestion(question);
    }

    @DeleteMapping("delete/{questionTitle}")
    public String deleteQuestionByTitle(@PathVariable String questionTitle){
        return questionService.deleteQuestionByTitle(questionTitle);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateQuestion(@PathVariable int id, @RequestBody Question updatedQuestion) {
        return questionService.updateQuestion(id, updatedQuestion);
    }

    /*ResponseEntity is a Spring class that lets you control the full HTTP response, including:
    Status code (e.g., 200 OK, 404 Not Found)
    Response body (message or data)
    Headers (if needed)*/

}
