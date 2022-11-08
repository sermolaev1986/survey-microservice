package org.example.surveymicroservice.service

import org.example.surveymicroservice.dto.AnswerDto
import org.example.surveymicroservice.dto.QuestionDto
import org.example.surveymicroservice.dto.SurveyParticipationDto
import org.example.surveymicroservice.exception.InvalidSurveyParticipationException
import org.example.surveymicroservice.model.Answer
import org.example.surveymicroservice.model.Question
import org.example.surveymicroservice.model.Survey
import org.example.surveymicroservice.redis.StatisticsRedisKeyProvider
import org.example.surveymicroservice.repository.SurveyStatisticsRepository
import spock.lang.Specification
import spock.lang.Subject

class SurveyParticipationServiceTest extends Specification {

    SurveyService surveyService = Stub()
    StatisticsRedisKeyProvider keyProvider = Stub()
    SurveyStatisticsRepository statisticsRepository = Mock()

    @Subject
    def participationService = new SurveyParticipationService(surveyService, statisticsRepository, keyProvider)

    def "should properly save statistics"() {
        given:
        surveyService.getSurveyById('survey-id') >> new Survey(id: 'survey-id', questions: [
                new Question(id: 'question-1', question: 'How do you do?', answers: [
                        new Answer(id: 'answer-1', answer: 'I am fine, thanks!'),
                        new Answer(id: 'answer-2', answer: 'Well, could have been better...'),
                        new Answer(id: 'answer-3', answer: 'How dare you ask me?!')
                ]),
                new Question(id: 'question-2', question: 'How is the weather today?', answers: [
                        new Answer(id: 'answer-1', answer: 'Sunny'),
                        new Answer(id: 'answer-2', answer: 'Cloudy'),
                        new Answer(id: 'answer-3', answer: 'Rainy'),
                        new Answer(id: 'answer-4', answer: 'How dare you ask me?!')
                ])
        ])

        keyProvider.getKeyFor('survey-id', 'question-1', 'answer-3') >> 'key-1'
        keyProvider.getKeyFor('survey-id', 'question-2', 'answer-1') >> 'key-2'
        keyProvider.getKeyFor('survey-id', 'question-2', 'answer-4') >> 'key-3'

        when:
        participationService.createSurveyParticipation('survey-id', new SurveyParticipationDto(questions: [
                new QuestionDto(id: 'question-1', answers: [
                        new AnswerDto(id: 'answer-3')
                ]),
                new QuestionDto(id: 'question-2', answers: [
                        new AnswerDto(id: 'answer-1'),
                        new AnswerDto(id: 'answer-4')
                ]),
        ]))

        then:
        1 * statisticsRepository.save('key-1')
        1 * statisticsRepository.save('key-2')
        1 * statisticsRepository.save('key-3')
    }

    def "should fail if question does not exist in survey"() {
        given:
        surveyService.getSurveyById('survey-id') >> new Survey(id: 'survey-id', questions: [
                new Question(id: 'question-1', question: 'How do you do?', answers: [
                        new Answer(id: 'answer-1', answer: 'I am fine, thanks!')
                ]),
                new Question(id: 'question-2', question: 'How is the weather today?', answers: [
                        new Answer(id: 'answer-1', answer: 'Sunny')
                ])
        ])

        when:
        participationService.createSurveyParticipation('survey-id', new SurveyParticipationDto(questions: [
                new QuestionDto(id: 'wrong-question', answers: [
                        new AnswerDto(id: 'answer-1')
                ])
        ]))

        then:
        InvalidSurveyParticipationException e = thrown()
        e.message == 'Question with id wrong-question does not exist in survey'
    }

    def "should fail if answer does not exist in survey"() {
        given:
        surveyService.getSurveyById('survey-id') >> new Survey(id: 'survey-id', questions: [
                new Question(id: 'question-1', question: 'How do you do?', answers: [
                        new Answer(id: 'right-answer', answer: 'I am fine, thanks!')
                ])
        ])

        when:
        participationService.createSurveyParticipation('survey-id', new SurveyParticipationDto(questions: [
                new QuestionDto(id: 'question-1', answers: [
                        new AnswerDto(id: 'wrong-answer'),
                        new AnswerDto(id: 'right-answer'),
                ])
        ]))

        then:
        InvalidSurveyParticipationException e = thrown()
        e.message == 'Answer with id wrong-answer does not exist in question question-1'
    }
}
