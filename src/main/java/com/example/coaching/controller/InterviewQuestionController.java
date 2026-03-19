package com.example.coaching.controller;
import com.example.coaching.model.InterviewQuestion;
import com.example.coaching.service.InterviewQuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/interview-questions") // Allow requests from your React app
public class InterviewQuestionController {

    @Autowired
    private InterviewQuestionService service;

    @PostMapping
    public ResponseEntity<InterviewQuestion> createQuestion(@RequestBody InterviewQuestion question) {
        InterviewQuestion savedQuestion = service.addQuestion(question);
        return new ResponseEntity<>(savedQuestion, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InterviewQuestion>> getAllQuestions() {
        List<InterviewQuestion> questions = service.getAllQuestions();
        return ResponseEntity.ok(questions);
    }
}