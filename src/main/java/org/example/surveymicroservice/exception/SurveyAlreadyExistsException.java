package org.example.surveymicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class SurveyAlreadyExistsException extends RuntimeException {
    public SurveyAlreadyExistsException(String surveyId) {
        super(String.format("Survey with id %s already exists", surveyId));
    }
}
