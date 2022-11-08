package org.example.surveymicroservice.dto;

import lombok.Getter;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
public class SurveyParticipationDto {
    @NotEmpty
    private List<QuestionDto> questions;
}
