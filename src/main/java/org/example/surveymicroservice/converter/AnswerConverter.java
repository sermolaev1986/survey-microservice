package org.example.surveymicroservice.converter;

import org.example.surveymicroservice.dto.AnswerDto;
import org.example.surveymicroservice.model.Answer;
import org.springframework.stereotype.Component;

@Component
public class AnswerConverter {

    public AnswerDto toDto(Answer answer) {
        var answerDto = new AnswerDto();
        answerDto.setId(answer.getId());
        answerDto.setAnswer(answer.getAnswer());
        return answerDto;
    }

    public Answer toModel(AnswerDto answerDto) {
        var answer = new Answer();
        answer.setId(answerDto.getId());
        answer.setAnswer(answerDto.getAnswer());
        return answer;
    }

}
