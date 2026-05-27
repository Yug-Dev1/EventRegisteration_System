package com.MiniProject.eventregistration.mongo.document;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "registration_answers")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationAnswers {

    @Id
    private String id;

    @NotNull
    @Positive
    private Long registrationId;

    @NotNull
    @Positive
    private Long userId;

    @NotNull
    @Positive
    private Long eventId;

    @NotEmpty(message = "Answers cannot be empty")
    private Map<String, Object> answers;
}