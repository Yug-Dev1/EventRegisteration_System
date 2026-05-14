package com.MiniProject.eventregistration.mongo.document;

import com.MiniProject.eventregistration.entity.Enums.EventType;
import com.MiniProject.eventregistration.entity.Enums.FormFieldType;
import com.MiniProject.eventregistration.entity.Enums.PaymentStatus;
import com.MiniProject.eventregistration.entity.Enums.TicketTierType;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Document(collection = "event_page_config")
public class EventPageConfig {

    @Id
    private String id;

    // Links Mongo config to MySQL Event
    private Long eventId;

    // MOVIE, MARATHON, PARTY, WORKSHOP, CONCERT, FESTIVAL, SPORTS, TECH_EVENT
    private EventType eventType;

    private MediaConfig media;

    private ThemeConfig theme;

    private List<ScheduleItem> schedule;

    private List<Participant> participants;

    private List<FaqItem> faq;

    private List<TicketTier> ticketTiers;

    // Event-specific metadata
    // Example:
    // MOVIE -> duration, genre, language
    // MARATHON -> distance, hydrationPoints
    // PARTY -> dressCode, djLineup
    private Map<String, Object> customAttributes;

    // Defines extra registration fields for THIS event
    // Actual user answers go in Registration table
    private List<FormField> customFormFields;


    // ---------------- MEDIA ----------------
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MediaConfig {
        private String bannerImageUrl;
        private String thumbnailUrl;
        private List<String> galleryImages;
        private String promoVideoUrl;
    }


    // ---------------- THEME ----------------
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ThemeConfig {
        private String primaryColor;
        private String secondaryColor;
        private String fontFamily;
        private String backgroundStyle;
        private String buttonStyle;
    }


    // ---------------- SCHEDULE ----------------
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScheduleItem {
        private String time;
        private String title;
        private String description;
    }


    // ---------------- PARTICIPANTS ----------------
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Participant {
        private String name;
        private String role;       // Speaker, Actor, DJ, Host, Trainer
        private String imageUrl;
        private String bio;
        private Map<String, String> socialLinks;
    }


    // ---------------- FAQ ----------------
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FaqItem {
        private String question;
        private String answer;
    }


    // ---------------- TICKETS ----------------
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TicketTier {
        private TicketTierType name;           // VIP, Gold, General
        private Double price;
        private Integer quantity;
        private List<String> benefits;
    }


    // ---------------- DYNAMIC REGISTRATION FIELDS ----------------
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FormField {
        private String label;
        private FormFieldType type;           // text, dropdown, checkbox, radio
        private boolean required;
        private List<String> options;
        private String placeholder;
    }
}