package org.example.surveymicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class SurveyMicroserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SurveyMicroserviceApplication.class, args);
    }

}
