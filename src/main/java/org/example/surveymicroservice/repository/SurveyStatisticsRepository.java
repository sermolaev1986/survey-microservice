package org.example.surveymicroservice.repository;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SurveyStatisticsRepository {

    private final RedisTemplate<String, Long> longRedisTemplate;

    /**
     * Saves statistics by given key. Implemented via redis increment operation.
     * This way we achieve atomic increment in concurrent environment at no cost.
     * Statistics calculated on the fly, so operation to get statistics is not going to be costly (pre-aggregated).
     *
     * @param statisticsKey - key of given statistic in redis
     */
    public void save(@NonNull String statisticsKey) {
        longRedisTemplate.opsForValue().increment(statisticsKey);
    }
}
