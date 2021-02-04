package com.ex.licenta1.domain.services;

import com.ex.licenta1.domain.entities.Category;
import com.ex.licenta1.domain.entities.Survey;
import com.ex.licenta1.domain.repositories.CategoryRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Optional<Category> add(Category category, Optional<Long> parentId){
        if(parentId.isPresent())
            category.setParentId(parentId.get());
        else
            category.setParentId(null);

        try{
            return Optional.of(this.categoryRepository.save(category));
        }catch(DataIntegrityViolationException e){
            return Optional.empty();
        }
    }

    public Collection<Category> get(){
        return categoryRepository.findAll();
    }


    public Optional<Category> getOne(Long parentId) {
        return this.categoryRepository.findById(parentId);
    }

    public void addSurvey(Survey survey, Category category) {
        category.addSurvey(survey);
        this.categoryRepository.save(category);
    }

    public Optional<Category> findByName(String categoryName) {
        return this.categoryRepository.findByName(categoryName);
    }

    public Collection<Category> getSubcategories(Long parentId) {
        return this.categoryRepository.findByParentId(parentId);
    }
}
