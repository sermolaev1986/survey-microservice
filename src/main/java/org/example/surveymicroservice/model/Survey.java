package org.example.surveymicroservice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.util.List;

@Data
@RedisHash("survey")
public class Survey {
    @Id
    private String id;
    private List<Question> questions;
}
