package org.example.surveymicroservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionDto {
    private String id;
    private String question;
    private List<AnswerDto> answers;
}
