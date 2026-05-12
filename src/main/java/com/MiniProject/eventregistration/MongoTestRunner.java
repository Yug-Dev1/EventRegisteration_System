package com.MiniProject.eventregistration;

import com.MiniProject.eventregistration.mongo.document.EventPageConfig;
import com.MiniProject.eventregistration.mongo.repository_mongo.EventPageConfigRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MongoTestRunner implements CommandLineRunner {
    private final EventPageConfigRepository repo;
    public MongoTestRunner(EventPageConfigRepository repo) {
        this.repo = repo;
    }
    @Override
    public void run(String... args) {
        repo.save(new EventPageConfig(101L, "dark", false));
        System.out.println("Inserted into Mongo");
    }
}
