package org.example.surveymicroservice.repository;

import org.example.surveymicroservice.model.Survey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends CrudRepository<Survey, String> {

}
