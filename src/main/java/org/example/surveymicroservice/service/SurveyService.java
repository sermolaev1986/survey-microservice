package org.example.surveymicroservice.service;

import lombok.RequiredArgsConstructor;
import org.example.surveymicroservice.exception.SurveyNotFoundException;
import org.example.surveymicroservice.model.Survey;
import org.example.surveymicroservice.repository.SurveyRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SurveyService {

    private final SurveyRepository surveyRepository;

    public void createSurvey(Survey survey) {
        surveyRepository.save((survey));
    }

    public List<Survey> getSurveys() {
        var surveys = new ArrayList<Survey>();
        surveyRepository.findAll().forEach(surveys::add);
        return surveys;
    }

    public Survey getSurveyById(String id) {
        return surveyRepository.findById(id)
                .orElseThrow(SurveyNotFoundException::new);
    }
}
