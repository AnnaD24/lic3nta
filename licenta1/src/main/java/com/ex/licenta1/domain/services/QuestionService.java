package com.ex.licenta1.domain.services;

import com.ex.licenta1.domain.entities.Question;
import com.ex.licenta1.domain.repositories.QuestionRepository;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collection;
import java.util.Optional;

@Service
public class QuestionService {

    Logger logger= LoggerFactory.getLogger(QuestionService.class);

    private QuestionRepository questionRepository;

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Collection<Question> getAll() {
       return this.questionRepository.findAll();
    }

    public Optional<Question> add(Question question) {
        try{
            return Optional.of(this.questionRepository.save(question));
        }catch(DataIntegrityViolationException e){
            return Optional.empty();
        }
    }

    public Optional<Question> getQuestion(Long questionId) {
        return this.questionRepository.findById(questionId);
    }
}
