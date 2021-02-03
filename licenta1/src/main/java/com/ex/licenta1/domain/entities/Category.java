package com.ex.licenta1.domain.entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "category")
public class Category {
    private @GeneratedValue @Id Long id;
    @Column(unique = true)
    private String name;
    private Long parentId;
    @OneToMany(mappedBy = "category")
    private Set<Survey> surveys = new HashSet<>();

    public Category() {
    }

    public void addSurvey(Survey survey){
        surveys.add(survey);
        survey.setCategory(this);
    }

    public Set<Survey> getSurveys() {
        return surveys;
    }

    public Category(String name, Long parentId) {
        this.name = name;
        this.parentId = parentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getParentId() {
        return parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(id, category.id) &&
                Objects.equals(name, category.name) &&
                Objects.equals(parentId, category.parentId) &&
                Objects.equals(surveys, category.surveys);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, parentId);
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", parentId=" + parentId +
                ", surveys=" + surveys +
                '}';
    }
}
