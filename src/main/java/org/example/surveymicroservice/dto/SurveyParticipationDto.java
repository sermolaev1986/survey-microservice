package org.example.surveymicroservice.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class SurveyParticipationDto {
    private List<QuestionDto> questions;
}
