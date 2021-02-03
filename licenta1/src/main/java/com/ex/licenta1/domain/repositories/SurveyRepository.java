package com.ex.licenta1.domain.repositories;

import com.ex.licenta1.domain.entities.Survey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SurveyRepository extends JpaRepository<Survey, Long> {
}
