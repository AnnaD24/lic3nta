package com.ex.licenta1.api;

import com.ex.licenta1.domain.entities.Question;
import com.ex.licenta1.domain.services.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;
import java.util.Optional;

@EnableSwagger2
@RestController
@RequestMapping("/question")
public class QuestionController {

    Logger logger = LoggerFactory.getLogger(QuestionController.class);

    private QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService=questionService;
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<Collection<Question>> getQuestions(){
        return new ResponseEntity<>(questionService.getAll(), HttpStatus.OK);
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        Optional<Question> foundQuestion = questionService.add(question);
        return foundQuestion
                .map(value -> new ResponseEntity<>(value + " added!", HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>("Can not add duplicated questions.", HttpStatus.BAD_REQUEST));
    }


}
