package org.example.surveymicroservice.converter

import org.example.surveymicroservice.dto.AnswerDto
import org.example.surveymicroservice.dto.QuestionDto
import org.example.surveymicroservice.dto.SurveyDto
import org.example.surveymicroservice.model.Answer
import org.example.surveymicroservice.model.Question
import org.example.surveymicroservice.model.Survey
import spock.lang.Specification
import spock.lang.Subject

class ConverterTest extends Specification {

    @Subject
    def surveyConverter = new SurveyConverter(new QuestionConverter(new AnswerConverter()))

    def "should convert survey from model to dto"() {
        given:
        def survey = new Survey(id: 'survey-id', questions: [
                new Question(id: 'question-1-id', question: 'What is your name?', answers: [
                        new Answer(id: 'answer-1-id', answer: 'Jack'),
                        new Answer(id: 'answer-2-id', answer: 'John')
                ]),
                new Question(id: 'question-2-id', question: 'Where do you live?', answers: [
                        new Answer(id: 'answer-3-id', answer: 'Vienna'),
                        new Answer(id: 'answer-4-id', answer: 'Paris')
                ])
        ])

        when:
        def dto = surveyConverter.toDto(survey)

        then:
        dto.id == 'survey-id'
        dto.questions.size() == 2
        dto.questions[0].id == 'question-1-id'
        dto.questions[0].question == 'What is your name?'
        dto.questions[0].answers.size() == 2
        dto.questions[0].answers[0].id == 'answer-1-id'
        dto.questions[0].answers[0].answer == 'Jack'
        dto.questions[0].answers[1].id == 'answer-2-id'
        dto.questions[0].answers[1].answer == 'John'

        dto.id == 'survey-id'
        dto.questions.size() == 2
        dto.questions[1].id == 'question-2-id'
        dto.questions[1].question == 'Where do you live?'
        dto.questions[1].answers.size() == 2
        dto.questions[1].answers[0].id == 'answer-3-id'
        dto.questions[1].answers[0].answer == 'Vienna'
        dto.questions[1].answers[1].id == 'answer-4-id'
        dto.questions[1].answers[1].answer == 'Paris'
    }

    def "should convert survey from dto to model"() {
        given:
        def dto = new SurveyDto(id: 'survey-id', questions: [
                new QuestionDto(id: 'question-1-id', question: 'What is your name?', answers: [
                        new AnswerDto(id: 'answer-1-id', answer: 'Jack'),
                        new AnswerDto(id: 'answer-2-id', answer: 'John')
                ]),
                new QuestionDto(id: 'question-2-id', question: 'Where do you live?', answers: [
                        new AnswerDto(id: 'answer-3-id', answer: 'Vienna'),
                        new AnswerDto(id: 'answer-4-id', answer: 'Paris')
                ])
        ])

        when:
        def model = surveyConverter.toModel(dto)

        then:
        model.with {
            assert id == 'survey-id'
            assert questions.size() == 2
            questions[0].with {
                assert id == 'question-1-id'
                assert question == 'What is your name?'
                assert answers.size() == 2
                answers[0].with {
                    assert id == 'answer-1-id'
                    assert answer == 'Jack'
                }
                answers[1].with {
                    assert id == 'answer-2-id'
                    assert answer == 'John'
                }
            }
            questions[1].with {
                assert id == 'question-2-id'
                assert question == 'Where do you live?'
                assert answers.size() == 2
                answers[0].with {
                    assert id == 'answer-3-id'
                    assert answer == 'Vienna'
                }
                answers[1].with {
                    assert id == 'answer-4-id'
                    assert answer == 'Paris'
                }
            }
        }


    }

    //exact behaviour to be discussed whether should be tolerated or not
    def "should tolerate null properties when converting from dto to model"() {
        given:
        def dto = new SurveyDto(id: null, questions: [
                new QuestionDto(id: null, question: null, answers: [
                        new AnswerDto(id: null, answer: null)
                ])
        ])

        when:
        def model = surveyConverter.toModel(dto)

        then:
        model.id == null
        model.questions[0].id == null
        model.questions[0].answers[0].id == null
    }
}
