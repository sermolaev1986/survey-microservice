package org.example.surveymicroservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.surveymicroservice.dto.SurveyParticipationDto;
import org.example.surveymicroservice.service.SurveyParticipationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/surveys")
@RequiredArgsConstructor
public class SurveyParticipationController {

    private final SurveyParticipationService surveyParticipationService;

    @PostMapping("/{surveyId}/participations")
    public void createSurveyParticipation(@PathVariable("surveyId") String surveyId, @RequestBody SurveyParticipationDto surveyParticipation) {
        surveyParticipationService.createSurveyParticipation(surveyId, surveyParticipation);
    }

}
