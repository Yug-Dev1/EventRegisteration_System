package com.MiniProject.eventregistration.mongo.repository_mongo;

import com.MiniProject.eventregistration.entity.Enums.EventType;
import com.MiniProject.eventregistration.mongo.document.EventPageConfig;
import com.MiniProject.eventregistration.mongo.document.RegistrationAnswers;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public interface EventPageConfigRepository
        extends MongoRepository<EventPageConfig, String> {

    Optional<EventPageConfig> findByEventId(Long eventId);
    boolean existsByEventId(Long eventId);

    void deleteByEventId(Long id);

    List<EventPageConfig> findByEventType(EventType eventType);
}
