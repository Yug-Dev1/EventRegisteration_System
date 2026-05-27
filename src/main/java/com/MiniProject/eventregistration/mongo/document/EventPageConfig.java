package com.MiniProject.eventregistration.mongo.document;

import com.MiniProject.eventregistration.entity.Enums.EventType;
import com.MiniProject.eventregistration.entity.Enums.FormFieldType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "event_page_config")
public class EventPageConfig {

    @Id
    private String id;

    @NotNull(message = "Event ID is required")
    @Positive(message = "Event ID must be positive")
    private Long eventId;

    @NotNull(message = "Event type is required")
    private EventType eventType;

    @Valid
    @NotNull(message = "Media config is required")
    private MediaConfig media;

    @Valid
    private ThemeConfig theme;

    @Valid
    private List<ScheduleItem> schedule;

    @Valid
    private List<Participant> participants;

    @Valid
    private List<FaqItem> faq;

    @Valid
    private List<TicketTier> ticketTiers;

    private Map<String, Object> customAttributes;

    @Valid
    private List<FormField> customFormFields;


    // MEDIA
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class MediaConfig {

        @NotBlank(message = "Banner image URL is required")
        private String bannerImageUrl;

        private String thumbnailUrl;

        private List<String> galleryImages;

        private String promoVideoUrl;
    }


    // THEME
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ThemeConfig {

        @Pattern(
                regexp = "^#([A-Fa-f0-9]{6})$",
                message = "Primary color must be valid hex code"
        )
        private String primaryColor;

        @Pattern(
                regexp = "^#([A-Fa-f0-9]{6})$",
                message = "Secondary color must be valid hex code"
        )
        private String secondaryColor;

        private String fontFamily;
        private String backgroundStyle;
        private String buttonStyle;
    }


    // SCHEDULE
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ScheduleItem {

        @NotBlank(message = "Schedule time is required")
        private String time;

        @NotBlank(message = "Schedule title is required")
        private String title;

        private String description;
    }


    // PARTICIPANTS
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Participant {

        @NotBlank(message = "Participant name is required")
        private String name;

        @NotBlank(message = "Participant role is required")
        private String role;

        private String imageUrl;

        @Size(max = 1000, message = "Bio too long")
        private String bio;

        private Map<String, String> socialLinks;
    }


    // FAQ
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FaqItem {

        @NotBlank(message = "FAQ question is required")
        private String question;

        @NotBlank(message = "FAQ answer is required")
        private String answer;
    }


    // TICKETS
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class TicketTier {

        @NotBlank(message = "Ticket name is required")
        private String name;

        @NotNull(message = "Ticket price is required")
        @PositiveOrZero(message = "Price cannot be negative")
        private Double price;

        @NotNull(message = "Ticket quantity is required")
        @Positive(message = "Quantity must be positive")
        private Integer quantity;

        private List<String> benefits;
    }


    // FORM FIELDS
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class FormField {

        @NotBlank(message = "Field label is required")
        private String label;

        @NotNull(message = "Field type is required")
        private FormFieldType type;

        private boolean required;

        private List<String> options;

        private String placeholder;
    }
}