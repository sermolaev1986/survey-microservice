package org.example.surveymicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.surveymicroservice.converter.SurveyConverter;
import org.example.surveymicroservice.dto.SurveyDto;
import org.example.surveymicroservice.repository.SurveyRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyRepository surveyRepository;
    private final SurveyConverter surveyConverter;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createSurvey(@RequestBody SurveyDto survey) {
        surveyRepository.save(surveyConverter.toModel(survey));
    }

    @GetMapping
    public List<SurveyDto> getSurveys() {
        var surveys = new ArrayList<SurveyDto>();
        surveyRepository.findAll().forEach(survey -> surveys.add(surveyConverter.toDto(survey)));
        return surveys;

    }


}
