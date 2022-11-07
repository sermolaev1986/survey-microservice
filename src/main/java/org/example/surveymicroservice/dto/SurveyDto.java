package org.example.surveymicroservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SurveyDto {
    private String id;
    private List<QuestionDto> questions;
}
