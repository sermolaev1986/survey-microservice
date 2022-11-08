package org.example.surveymicroservice.service;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.surveymicroservice.dto.AnswerDto;
import org.example.surveymicroservice.dto.QuestionDto;
import org.example.surveymicroservice.dto.SurveyParticipationDto;
import org.example.surveymicroservice.exception.InvalidSurveyParticipationException;
import org.example.surveymicroservice.model.Answer;
import org.example.surveymicroservice.model.Question;
import org.example.surveymicroservice.redis.StatisticsRedisKeyProvider;
import org.example.surveymicroservice.repository.SurveyStatisticsRepository;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SurveyParticipationService {

    private final SurveyService surveyService;
    private final SurveyStatisticsRepository surveyStatisticsRepository;
    private final StatisticsRedisKeyProvider statisticsRedisKeyProvider;

    public void createSurveyParticipation(@NonNull String surveyId, @NonNull SurveyParticipationDto surveyParticipation) {
        log.debug("New participation for survey {}", surveyId);
        var surveyTemplate = surveyService.getSurveyById(surveyId);
        Map<String, Question> questionsPool = surveyTemplate.getQuestions().stream()
                .collect(Collectors.toMap(Question::getId, Function.identity()));
        for (QuestionDto question : surveyParticipation.getQuestions()) {
            if (!questionsPool.containsKey(question.getId())) {
                var message = String.format("Question with id %s does not exist in survey", question.getId());
                log.warn("Invalid survey participation: {}", message);
                throw new InvalidSurveyParticipationException(message);
            }
            var answersPool = questionsPool.get(question.getId()).getAnswers().stream()
                    .map(Answer::getId)
                    .collect(Collectors.toSet());
            for (AnswerDto answer : question.getAnswers()) {
                if (!answersPool.contains(answer.getId())) {
                    var message = String.format("Answer with id %s does not exist in question %s", answer.getId(), question.getId());
                    log.warn("Invalid survey participation: {}", message);
                    throw new InvalidSurveyParticipationException(message);
                }
                var key = statisticsRedisKeyProvider.getKeyFor(surveyTemplate.getId(), question.getId(), answer.getId());
                log.debug("Updating statistics for key {}", key);
                surveyStatisticsRepository.save(key);
            }
        }
    }
}
