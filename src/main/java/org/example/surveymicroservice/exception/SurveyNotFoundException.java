package org.example.surveymicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class SurveyNotFoundException extends RuntimeException {
    public SurveyNotFoundException(String surveyId) {
        super(String.format("Survey with id %s not found", surveyId));
    }
}
