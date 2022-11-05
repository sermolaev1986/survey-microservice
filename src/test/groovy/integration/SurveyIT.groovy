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
        when:
        def response = RestAssured
                .given()
                .port(port)
                .when()
                .get('/surveys/1')

        then:
        response.then()
                .log().all()
                .statusCode(404)

        when:
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

        then:
        response.then()
                .log().all()
                .statusCode(201)

        when:
        response = RestAssured
                .given()
                .port(port)
                .when()
                .get('/surveys/1')

        then:
        response.then()
                .log().all()
                .statusCode(200)
    }
}
