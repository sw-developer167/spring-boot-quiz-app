package com.project.quizapp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor // it will constructore
public class Response {
    private Integer id;
    private String response;

}
