package com.MiniProject.eventregistration.mongo.repository_mongo;

import com.MiniProject.eventregistration.mongo.document.EventPageConfig;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface EventPageConfigRepository
        extends MongoRepository<EventPageConfig, String> {
    Optional<EventPageConfig> findByEventId(Long eventId);
    boolean existsByEventId(Long eventId);
}
