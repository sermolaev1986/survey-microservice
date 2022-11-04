package org.example.surveymicroservice.converter;

import lombok.RequiredArgsConstructor;
import org.example.surveymicroservice.dto.SurveyDto;
import org.example.surveymicroservice.model.Survey;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SurveyConverter {

    private final QuestionConverter questionConverter;

    public SurveyDto toDto(Survey survey) {
        var surveyDto = new SurveyDto();
        surveyDto.setId(survey.getId());
        surveyDto.setQuestions(survey.getQuestions().stream()
                .map(questionConverter::toDto)
                .collect(Collectors.toList()));
        return surveyDto;
    }

    public Survey toModel(SurveyDto surveyDto) {
        var survey = new Survey();
        survey.setId(surveyDto.getId());
        survey.setQuestions(surveyDto.getQuestions().stream()
                .map(questionConverter::toModel)
                .collect(Collectors.toList()));
        return survey;
    }

}
