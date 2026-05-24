package com.MiniProject.eventregistration.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationRequestDTO {

    @NotNull(message = "Event ID is required")
    private Long eventId;

    @NotBlank(message = "Ticket tier is required")
    private String ticketTierName;

    @NotNull(message = "Ticket count is required")
    @Min(value = 1, message = "At least 1 ticket must be booked")
    private Integer ticketCount;

    // Dynamic answers for custom form fields
    private Map<String, Object> answers;
}