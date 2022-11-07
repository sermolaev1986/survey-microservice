package org.example.surveymicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.surveymicroservice.converter.SurveyConverter;
import org.example.surveymicroservice.dto.SurveyDto;
import org.example.surveymicroservice.service.SurveyService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/surveys")
@RequiredArgsConstructor
public class SurveyController {

    private final SurveyService surveyService;
    private final SurveyConverter surveyConverter;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public void createSurvey(@RequestBody SurveyDto survey) {
        surveyService.createSurvey(surveyConverter.toModel(survey));
    }

    @GetMapping("/{id}")
    public SurveyDto getSurveyById(@PathVariable("id") String id) {
        return surveyConverter.toDto(surveyService.getSurveyById(id));
    }

}
