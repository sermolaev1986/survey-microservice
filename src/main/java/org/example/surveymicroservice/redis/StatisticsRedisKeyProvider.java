package org.example.surveymicroservice.redis;

import lombok.NonNull;
import org.springframework.stereotype.Component;

@Component
public class StatisticsRedisKeyProvider {

    private static final String STATISTICS_NAMESPACE = "statistics";

    public String getKeyFor(@NonNull String surveyId, @NonNull String questionId, @NonNull String answerId) {
        return String.format("%s:%s:%s:%s", STATISTICS_NAMESPACE, surveyId, questionId, answerId);
    }
}
