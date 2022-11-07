package org.example.surveymicroservice.redis;

import org.springframework.stereotype.Component;

@Component
public class StatisticsRedisKeyProvider {

    private static final String STATISTICS_NAMESPACE = "statistics";

    public String getKeyFor(String surveyId, String questionId, String answerId) {
        return String.format("%s:%s:%s:%s", STATISTICS_NAMESPACE, surveyId, questionId, answerId);
    }
}
