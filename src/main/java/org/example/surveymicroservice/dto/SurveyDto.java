package org.example.surveymicroservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class SurveyDto {
    @NotBlank
    private String id;
    @NotEmpty
    private List<QuestionDto> questions;
}
