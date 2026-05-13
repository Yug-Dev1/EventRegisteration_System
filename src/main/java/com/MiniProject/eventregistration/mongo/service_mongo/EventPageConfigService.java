package com.MiniProject.eventregistration.mongo.service_mongo;

import com.MiniProject.eventregistration.mongo.document.EventPageConfig;
import com.MiniProject.eventregistration.mongo.repository_mongo.EventPageConfigRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class EventPageConfigService {

    private final EventPageConfigRepository eventPageConfigRepository;

    public void createDefaultConfig(Long eventId) {

        if (eventPageConfigRepository.existsByEventId(eventId)) {
            return;
        }

        EventPageConfig defaultConfig = EventPageConfig.builder()
                .eventId(eventId)
                .eventType("GENERAL")
                .media(EventPageConfig.MediaConfig.builder()
                        .bannerImageUrl("")
                        .thumbnailUrl("")
                        .galleryImages(new ArrayList<>())
                        .promoVideoUrl("")
                        .build())
                .theme(EventPageConfig.ThemeConfig.builder()
                        .primaryColor("#2563eb")
                        .secondaryColor("#ffffff")
                        .fontFamily("Poppins")
                        .backgroundStyle("light")
                        .buttonStyle("rounded")
                        .build())
                .schedule(new ArrayList<>())
                .participants(new ArrayList<>())
                .faq(new ArrayList<>())
                .ticketTiers(new ArrayList<>())
                .customFormFields(new ArrayList<>())
                .customAttributes(new HashMap<>())
                .build();

        eventPageConfigRepository.save(defaultConfig);
    }
}