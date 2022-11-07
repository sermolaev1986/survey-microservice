package org.example.surveymicroservice.service;

import lombok.RequiredArgsConstructor;
import org.example.surveymicroservice.dto.AnswerDto;
import org.example.surveymicroservice.dto.QuestionDto;
import org.example.surveymicroservice.dto.SurveyParticipationDto;
import org.example.surveymicroservice.exception.InvalidSurveyParticipationException;
import org.example.surveymicroservice.model.Question;
import org.example.surveymicroservice.redis.StatisticsRedisKeyProvider;
import org.example.surveymicroservice.repository.SurveyStatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SurveyParticipationService {

    private final SurveyService surveyService;
    private final SurveyStatisticsRepository surveyStatisticsRepository;
    private final StatisticsRedisKeyProvider statisticsRedisKeyProvider;

    public void createSurveyParticipation(String surveyId, SurveyParticipationDto surveyParticipation) {
        var surveyTemplate = surveyService.getSurveyById(surveyId);
        var questionsPool = surveyTemplate.getQuestions().stream()
                .map(Question::getId)
                .collect(Collectors.toSet());
        for (QuestionDto question : surveyParticipation.getQuestions()) {
            if (!questionsPool.contains(question.getId())) {
                throw new InvalidSurveyParticipationException(String.format("Question with id %s does not exist in survey", question.getId()));
            }
            var answersPool = question.getAnswers().stream()
                    .map(AnswerDto::getId)
                    .collect(Collectors.toSet());
            for (AnswerDto answer : question.getAnswers()) {
                if (!answersPool.contains(answer.getId())) {
                    throw new InvalidSurveyParticipationException(String.format("Answer with id %s does not exist in question %s", answer.getId(), question.getId()));
                }
                surveyStatisticsRepository.save(statisticsRedisKeyProvider.getKeyFor(surveyTemplate.getId(), question.getId(), answer.getId()));
            }
        }
    }
}
