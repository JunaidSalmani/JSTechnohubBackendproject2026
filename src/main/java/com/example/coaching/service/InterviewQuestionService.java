package com.example.coaching.service;
import com.example.coaching.model.InterviewQuestion;
import com.example.coaching.repository.InterviewQuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewQuestionService {

    @Autowired
    private InterviewQuestionRepository repository;

    public List<InterviewQuestion> getAllQuestions() {
        // Return newest questions first
        return repository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
    }

    public InterviewQuestion addQuestion(InterviewQuestion question) {
        return repository.save(question);
    }
}