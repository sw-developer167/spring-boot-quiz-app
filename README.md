# Spring Boot Quiz Application

A simple Quiz backend API using Spring Boot, PostgreSQL, and Postman.

## Features
- CRUD operations for questions
- Filter questions by category
- Create random quizzes
- RESTful API using Spring Boot
- PostgreSQL for database
- Built with Maven

## Technologies
- Java 17
- Spring Boot
- Spring Web, Spring Data JPA, Lombok
- PostgreSQL
- IntelliJ IDEA
- Postman

## API Endpoints
- `GET question/allquestions` - get all questions
- `GET question/category/{category}` - get questions by category
- `POST question/add` - add a new question
- `PUT question/update/{id}` - update question
- `DELETE question/delete/{questionTitle}` - delete question
- `GET quiz/create` - create quiz 
- `GET quiz/get/{id}` - get questions from quiz with quiz id
- `POST quiz/submit/{id}` - submit responses for the quiz
