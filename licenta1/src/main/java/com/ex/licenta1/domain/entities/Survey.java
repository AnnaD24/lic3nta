package com.ex.licenta1.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "survey")
public class Survey {
    private @Id @GeneratedValue Long id;
    @Column(unique = true)
    private String title;
    @ManyToMany
    @JoinTable(
            name = "question_survey",
            joinColumns = @JoinColumn(name = "survey_id"),
            inverseJoinColumns = @JoinColumn(name = "question_id")
    )
    private Set<Question> questions = new HashSet<>();
    @JsonIgnore
    @ManyToOne
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Survey(String title) {
        this.title = title;
    }

    public Survey() {
    }

    public void addQuestion(Question question){
        questions.add(question);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        return Objects.equals(id, survey.id) &&
                Objects.equals(title, survey.title) &&
                Objects.equals(questions, survey.questions) &&
                Objects.equals(category, survey.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, questions, category);
    }

    @Override
    public String toString() {
        return "Survey{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", questions=" + questions +
                '}';
    }
}
