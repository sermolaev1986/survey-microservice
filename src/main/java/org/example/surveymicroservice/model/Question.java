package org.example.surveymicroservice.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Question {
    private String id;
    private String question;
    private List<Answer> answers;
}
