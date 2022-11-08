package integration

import io.restassured.RestAssured
import org.example.surveymicroservice.SurveyMicroserviceApplication
import org.example.surveymicroservice.model.Answer
import org.example.surveymicroservice.model.Question
import org.example.surveymicroservice.model.Survey
import org.example.surveymicroservice.repository.SurveyRepository
import org.hamcrest.Matchers
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.data.redis.core.RedisTemplate
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SurveyMicroserviceApplication)
class SurveyParticipationIT extends Specification {

    @LocalServerPort
    int port

    @Autowired
    SurveyRepository surveyRepository

    @Autowired
    RedisTemplate<String, Long> longRedisTemplate

    def "should successfully participate in survey"() {
        given:
        surveyRepository.save(new Survey(id: 'my-survey', questions: [
                new Question(id: 'question-1', question: "What's up, bro?", answers: [
                        new Answer(id: 'answer-1', answer: 'All good, thanks for asking'),
                        new Answer(id: 'answer-2', answer: 'Have seen better times'),
                        new Answer(id: 'answer-3', answer: 'No comments'),
                        new Answer(id: 'answer-4', answer: 'How dare you ask me!?')
                ]),
                new Question(id: 'question-2', question: 'What do you do for living?', answers: [
                        new Answer(id: 'answer-1', answer: 'Coding'),
                        new Answer(id: 'answer-2', answer: 'Boozing and partying')
                ])
        ]))

        when:
        def response = RestAssured
                .given()
                .port(port)
                .header('content-type', 'application/json')
                .body("""{
                    "questions": [
                        {
                            "id": "question-1",
                            "answers": [
                                {
                                    "id": "answer-1"
                                },
                                {
                                    "id": "answer-3"
                                },
                                {
                                    "id": "answer-4"
                                }
                            ]
                        },
                        {
                            "id": "question-2",
                            "answers": [
                                {
                                    "id": "answer-2"
                                }
                            ]
                        }
                    ]
                }""")
                .when()
                .post('/surveys/my-survey/participations')

        then:
        response.then()
                .log().all()
                .statusCode(200)

        //since statistics endpoint does not exist, the only way to test it so far is via exploration of redis
        longRedisTemplate.opsForValue().get('statistics:my-survey:question-1:answer-1') == 1
        longRedisTemplate.opsForValue().get('statistics:my-survey:question-1:answer-2') == null
        longRedisTemplate.opsForValue().get('statistics:my-survey:question-1:answer-3') == 1
        longRedisTemplate.opsForValue().get('statistics:my-survey:question-1:answer-4') == 1
        longRedisTemplate.opsForValue().get('statistics:my-survey:question-2:answer-1') == null
        longRedisTemplate.opsForValue().get('statistics:my-survey:question-2:answer-2') == 1

        when:
        response = RestAssured
                .given()
                .port(port)
                .header('content-type', 'application/json')
                .body("""{
                    "questions": [
                        {
                            "id": "question-1",
                            "answers": [
                                {
                                    "id": "answer-3"
                                },
                                {
                                    "id": "answer-4"
                                }
                            ]
                        },
                        {
                            "id": "question-2",
                            "answers": [
                                {
                                    "id": "answer-1"
                                },
                                {
                                    "id": "answer-2"
                                }
                            ]
                        }
                    ]
                }""")
                .when()
                .post('/surveys/my-survey/participations')

        then:
        response.then().statusCode(200)

        //since statistics endpoint does not exist, the only way to test it so far is via exploration of redis
        longRedisTemplate.opsForValue().get('statistics:my-survey:question-1:answer-1') == 1
        longRedisTemplate.opsForValue().get('statistics:my-survey:question-1:answer-2') == null
        longRedisTemplate.opsForValue().get('statistics:my-survey:question-1:answer-3') == 2
        longRedisTemplate.opsForValue().get('statistics:my-survey:question-1:answer-4') == 2
        longRedisTemplate.opsForValue().get('statistics:my-survey:question-2:answer-1') == 1
        longRedisTemplate.opsForValue().get('statistics:my-survey:question-2:answer-2') == 2

    }

    def "should fail if trying to participate in not existing survey"() {
        when: "try to participate in not existing survey"
        def response = RestAssured
                .given()
                .port(port)
                .header('content-type', 'application/json')
                .body("""{
                
                }""")
                .when()
                .post('/surveys/not-existing/participations')

        then:
        response.then()
                .log().all()
                .statusCode(404)
                .body('message', Matchers.is('Survey with id not-existing not found'))
    }
}
