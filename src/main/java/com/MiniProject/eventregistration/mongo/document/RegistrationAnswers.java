package com.MiniProject.eventregistration.mongo.document;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    private Long registrationId;

    private Long userId;

    private Long eventId;

    private Map<String, Object> answers;

}

