package org.example.surveymicroservice.redis;

import lombok.extern.slf4j.Slf4j;
import org.example.surveymicroservice.configuration.RedisProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Embedded redis for local use and for integration tests. Can be enabled and disabled using "local" spring profile.
 */
@Configuration
@Slf4j
@Profile("local")
public class EmbeddedRedisServer {

    private final RedisServer redisServer;

    public EmbeddedRedisServer(RedisProperties redisProperties) {
        this.redisServer = new RedisServer(redisProperties.getPort());
        log.info("Embedded redis server configured with port {}.", redisProperties.getPort());
    }

    @PostConstruct
    public void postConstruct() {
        log.info("Starting embedded redis server...");
        redisServer.start();
        log.info("Embedded redis server started.");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("Stopping embedded redis server...");
        redisServer.stop();
        log.info("Embedded redis server stopped.");
    }
}
