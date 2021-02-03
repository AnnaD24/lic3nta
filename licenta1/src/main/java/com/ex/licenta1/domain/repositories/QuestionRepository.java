package com.ex.licenta1.domain.repositories;

import com.ex.licenta1.domain.entities.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
