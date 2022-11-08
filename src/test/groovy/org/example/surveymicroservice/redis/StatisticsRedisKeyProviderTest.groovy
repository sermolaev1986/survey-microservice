package org.example.surveymicroservice.redis

import spock.lang.Specification
import spock.lang.Subject

class StatisticsRedisKeyProviderTest extends Specification {

    @Subject
    def keyProvider = new StatisticsRedisKeyProvider()

    def "should compile proper redis key"() {
        when:
        def actual = keyProvider.getKeyFor(surveyId, questionId, answerId)

        then:
        actual == expected

        where:
        surveyId    | questionId    | answerId   | expected
        'survey-id' | 'question-id' | 'answerId' | 'statistics:survey-id:question-id:answerId'
        //probably should be also rejected to avoid collisions and error cases
        ''          | ''            | ''         | 'statistics:::'
    }

    def "should fail fast for null arguments"() {
        when:
        keyProvider.getKeyFor(surveyId, questionId, answerId)

        then:
        NullPointerException e = thrown()
        e.message == expected

        where:
        surveyId    | questionId    | answerId   | expected
        'survey-id' | 'question-id' | null       | 'answerId is marked non-null but is null'
        'survey-id' | null          | 'answerId' | 'questionId is marked non-null but is null'
        null        | 'question-id' | 'answerId' | 'surveyId is marked non-null but is null'
    }
}
