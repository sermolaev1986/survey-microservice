package org.example.surveymicroservice.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SurveyStatisticsRepository {

    private final RedisTemplate<String, Long> longRedisTemplate;

    public void save(@NonNull String statisticsKey) {
        longRedisTemplate.opsForValue().increment(statisticsKey);
    }
}
