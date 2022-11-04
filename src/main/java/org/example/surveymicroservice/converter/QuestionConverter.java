package org.example.surveymicroservice.converter;

import lombok.RequiredArgsConstructor;
import org.example.surveymicroservice.dto.QuestionDto;
import org.example.surveymicroservice.model.Question;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class QuestionConverter {

    private final AnswerConverter answerConverter;

    public QuestionDto toDto(Question question) {
        var questionDto = new QuestionDto();
        questionDto.setId(question.getId());
        questionDto.setQuestion(question.getQuestion());
        questionDto.setAnswers(question.getAnswers().stream()
                .map(answerConverter::toDto)
                .collect(Collectors.toList()));
        return questionDto;
    }

    public Question toModel(QuestionDto questionDto) {
        var question = new Question();
        question.setId(questionDto.getId());
        question.setQuestion(questionDto.getQuestion());
        question.setAnswers(questionDto.getAnswers().stream()
                .map(answerConverter::toModel)
                .collect(Collectors.toList()));
        return question;
    }
}
