package org.example.surveymicroservice.service;

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

    public void createSurvey(Survey survey) {
        surveyRepository.findById(survey.getId()).ifPresent(s -> {
            throw new SurveyAlreadyExistsException(survey.getId());
        });
        log.debug("Creating a survey {}", survey);
        surveyRepository.save((survey));
    }

    public Survey getSurveyById(String id) {
        return surveyRepository.findById(id)
                .orElseThrow(() -> new SurveyNotFoundException(id));
    }
}
