package org.example.surveymicroservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidSurveyParticipationException extends RuntimeException {
    public InvalidSurveyParticipationException(String message) {
        super(message);
    }
}
