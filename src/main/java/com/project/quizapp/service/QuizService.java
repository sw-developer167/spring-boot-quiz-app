package com.project.quizapp.service;

import com.project.quizapp.dao.QuestionDao;
import com.project.quizapp.dao.QuizDao;
import com.project.quizapp.model.Question;
import com.project.quizapp.model.QuestionWrapper;
import com.project.quizapp.model.Quiz;
import com.project.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;
    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {
        List<Question> questions = questionDao.findRandomQuestionsByCategory(category,numQ);
        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("success", HttpStatus.CREATED);

    }

    // This method retrieves the list of questions for a given quiz ID
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(int id) {

        // Attempt to retrieve the quiz from the database using the quiz ID
        Optional<Quiz> quiz = quizDao.findById(id);

        // Get the list of Question entities associated with the quiz
        // Note: quiz.get() assumes the quiz is present; this may throw an exception if not found
        List<Question> questionsFromDB = quiz.get().getQuestions();

        // Prepare a list to store only the necessary fields to send to the user
        List<QuestionWrapper> questionForUser = new ArrayList<>();

        // Iterate over each question retrieved from the database
        for (Question q : questionsFromDB) {
            // Wrap the question data into a simplified QuestionWrapper object
            // (excluding sensitive or unnecessary fields like the correct answer)
            QuestionWrapper qw = new QuestionWrapper(
                    q.getId(),
                    q.getQuestionTitle(),
                    q.getOption1(),
                    q.getOption2(),
                    q.getOption3(),
                    q.getOption4()
            );

            // Add the wrapped question to the list
            questionForUser.add(qw);
        }

        // Return the list of wrapped questions with HTTP status 200 OK
        return new ResponseEntity<>(questionForUser, HttpStatus.OK);
    }

    // This method calculates the quiz result based on the user's submitted responses
    public ResponseEntity<Integer> calculateResult(int id, List<Response> responses) {

        // Retrieve the quiz using the provided ID
        // Note: Using .get() assumes the quiz exists and may throw NoSuchElementException if not found
        Quiz quiz = quizDao.findById(id).get();

        // Extract the list of questions associated with the quiz
        List<Question> questions = quiz.getQuestions();

        // Counter to track the number of correct answers
        int right = 0;

        // Index to match each user response with the corresponding question
        int i = 0;

        // Loop through each user response
        for (Response response : responses) {
            // Compare the user's answer with the correct answer from the question
            if (response.getResponse().equals(questions.get(i).getRightAnswer())) {
                // If the answer is correct, increment the correct answer counter
                right++;
            }
            // Move to the next question
            i++;
        }

        // Return the total number of correct answers with HTTP 200 OK
        return new ResponseEntity<>(right, HttpStatus.OK);
    }

}
