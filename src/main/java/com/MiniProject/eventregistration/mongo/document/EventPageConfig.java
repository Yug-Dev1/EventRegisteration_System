package com.MiniProject.eventregistration.mongo.document;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "event_page_config")
public class EventPageConfig {

    @Id
    private String id;

    private Long eventId;

    private String theme;

    private Boolean published;

    public EventPageConfig() {
    }

    public EventPageConfig(Long eventId, String theme, Boolean published) {
        this.eventId = eventId;
        this.theme = theme;
        this.published = published;
    }

    public String getId() {
        return id;
    }

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }
}