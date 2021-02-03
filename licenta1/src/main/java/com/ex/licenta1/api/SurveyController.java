package com.ex.licenta1.api;
import com.ex.licenta1.domain.entities.Category;
import com.ex.licenta1.domain.entities.Question;
import com.ex.licenta1.domain.entities.Survey;
import com.ex.licenta1.domain.services.CategoryService;
import com.ex.licenta1.domain.services.QuestionService;
import com.ex.licenta1.domain.services.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;
import java.util.Optional;

@EnableSwagger2
@RestController
@RequestMapping("/survey")
public class SurveyController {
    private SurveyService surveyService;
    private QuestionService questionService;
    private CategoryService categoryService;

    public SurveyController(SurveyService surveyService, QuestionService questionService, CategoryService categoryService) {
        this.surveyService = surveyService;
        this.questionService = questionService;
        this.categoryService = categoryService;
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<Collection<Survey>> getSurveys(){
        return new ResponseEntity<>(surveyService.getSurveys(), HttpStatus.OK);
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<String> addSurvey(@RequestBody Survey survey, @RequestParam String categoryName){
        Optional<Category> foundCategory = categoryService.findByName(categoryName);

        if(foundCategory.isEmpty())
            return new ResponseEntity<>("Category named " + categoryName + " not found.",HttpStatus.NOT_FOUND);

        categoryService.addSurvey(survey, foundCategory.get());

        Optional<Survey> foundQuestion = surveyService.add(survey);

        return foundQuestion
                .map(value -> new ResponseEntity<>(value + " added!", HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>("Survey with title " + survey.getTitle() + " already exists.", HttpStatus.BAD_REQUEST));
    }

    @PutMapping()
    @ResponseBody
    public ResponseEntity<String> addQuestion(@RequestParam Long questionId, @RequestParam Long surveyId){
        Optional<Survey> survey = this.surveyService.getSurvey(surveyId);
        Optional<Question> question = this.questionService.getQuestion(questionId);

        if(survey.isEmpty())
            return new ResponseEntity<>("Survey with id " + surveyId + " not found.",HttpStatus.NOT_FOUND);
        if(question.isEmpty())
            return new ResponseEntity<>("Question with id " + questionId + " not found.",HttpStatus.NOT_FOUND);

        return this.surveyService.addQuestion(survey.get(), question.get())
                .map(value -> new ResponseEntity<>(value + " updated.", HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>("Survey with id " + surveyId + " already has question with id " + questionId, HttpStatus.BAD_REQUEST));
    }
}
