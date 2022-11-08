package org.example.surveymicroservice.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
public class QuestionDto {
    @NotBlank
    private String id;
    @NotBlank
    private String question;
    @NotEmpty
    private List<AnswerDto> answers;
}
