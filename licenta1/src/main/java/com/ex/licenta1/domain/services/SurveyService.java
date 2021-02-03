package com.ex.licenta1.domain.services;

import com.ex.licenta1.domain.entities.Question;
import com.ex.licenta1.domain.entities.Survey;
import com.ex.licenta1.domain.repositories.QuestionRepository;
import com.ex.licenta1.domain.repositories.SurveyRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class SurveyService {
    private SurveyRepository surveyRepository;
    private QuestionRepository questionRepository;
    private CategoryService categoryService;

    public SurveyService(SurveyRepository surveyRepository, QuestionRepository questionRepository, CategoryService categoryService) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.categoryService = categoryService;
    }

    public Collection<Survey> getSurveys() {
        return this.surveyRepository.findAll();
    }

    public Optional<Survey> add(Survey survey) {
        try{
            return Optional.of(this.surveyRepository.save(survey));
        }catch(DataIntegrityViolationException e){
            return Optional.empty();
        }
    }

    public Optional<Survey> getSurvey(Long surveyId) {
        return surveyRepository.findById(surveyId);
    }

    @Transactional
    public Optional<Survey> addQuestion(Survey survey, Question question) {

        if(survey.getQuestions().contains(question))
            return Optional.empty();

        survey.addQuestion(question);
        question.addSurvey(survey);

        questionRepository.save(question);

        return Optional.of(surveyRepository.save(survey));

    }
}
