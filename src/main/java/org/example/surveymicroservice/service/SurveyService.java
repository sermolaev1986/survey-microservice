package org.example.surveymicroservice.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.surveymicroservice.exception.SurveyAlreadyExistsException;
import org.example.surveymicroservice.exception.SurveyNotFoundException;
import org.example.surveymicroservice.model.Survey;
import org.example.surveymicroservice.repository.SurveyRepository;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    /**
     * Creates a new survey
     *
     * @param survey survey to be created
     * @throws SurveyAlreadyExistsException if survey with given id already exists
     */
    public void createSurvey(@NonNull Survey survey) {
        surveyRepository.findById(survey.getId()).ifPresent(s -> {
            throw new SurveyAlreadyExistsException(survey.getId());
        });
        log.debug("Creating a survey {}", survey);
        surveyRepository.save((survey));
    }

    /**
     * Gets survey by id
     * @param id survey id
     * @return {@link Survey}
     * @throws SurveyNotFoundException if survey with given id has not been found
     */
    public Survey getSurveyById(@NonNull String id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new SurveyNotFoundException(id));
    }
}
