package integration

import io.restassured.RestAssured
import org.example.surveymicroservice.SurveyMicroserviceApplication
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import spock.lang.Specification

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = SurveyMicroserviceApplication)
class SurveyIT extends Specification {

    @LocalServerPort
    int port

    def "survey creation flow"() {
        when: "try to get survey before creation"
        def response = RestAssured
                .given()
                .port(port)
                .when()
                .get('/surveys/1')

        then: "should respond with not found"
        response.then()
                .log().all()
                .statusCode(404)

        when: "create a survey for the first time"
        response = RestAssured
                .given()
                .port(port)
                .when()
                .header('Content-Type', 'application/json')
                .body("""
                    {
                      "id": "1",
                      "questions": [
                        {
                          "id": "1",
                          "question": "Question?",
                          "answers": [
                            {
                              "id": "1",
                              "answer": "My Answer"
                            }
                          ]
                        }
                      ]
                    }
                """)
                .post('/surveys')

        then: "should respond with created"
        response.then()
                .log().all()
                .statusCode(201)

        when: "get survey after creation"
        response = RestAssured
                .given()
                .port(port)
                .when()
                .get('/surveys/1')

        then: "should return created survey"
        response.then()
                .log().all()
                .statusCode(200)

        when: "try to create a survey with the same id"
        response = RestAssured
                .given()
                .port(port)
                .when()
                .header('Content-Type', 'application/json')
                .body("""
                    {
                      "id": "1",
                      "questions": [
                        {
                          "id": "1",
                          "question": "Question?",
                          "answers": [
                            {
                              "id": "1",
                              "answer": "My Answer"
                            }
                          ]
                        }
                      ]
                    }
                """)
                .post('/surveys')

        then: "should reject request"
        response.then()
                .log().all()
                .statusCode(409)
    }
}
