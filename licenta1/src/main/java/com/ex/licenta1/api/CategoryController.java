package com.ex.licenta1.api;

import com.ex.licenta1.domain.entities.Category;
import com.ex.licenta1.domain.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;

@EnableSwagger2
@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping()
    @ResponseBody
    public ResponseEntity<Collection<Category>> getAll(){
        return new ResponseEntity<>(categoryService.get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Collection<Category>> getSubcategories(@PathVariable(name="id") String parentId){
        try{
            Long id = Long.parseLong(parentId);
            return new ResponseEntity<>(categoryService.getSubcategories(id),HttpStatus.OK);
        }catch(NumberFormatException e){
            return new ResponseEntity<>(new HashSet<>(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping()
    @ResponseBody
    public ResponseEntity<String> addCategory(@RequestBody Category category, @RequestParam Optional<Long> parentId){
        return categoryService.add(category, parentId)
                .map(value -> new ResponseEntity<>(value + " added!", HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>("Can not add duplicated categories.", HttpStatus.BAD_REQUEST));
    }
}
