package com.MiniProject.eventregistration.mongo.repository_mongo;

import com.MiniProject.eventregistration.mongo.document.RegistrationAnswers;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RegistrationAnswerRepository extends MongoRepository<RegistrationAnswers, String> {

    Optional<RegistrationAnswers> findByRegistrationId(Long registrationId);
    void deleteByRegistrationId(Long registrationId);

}
