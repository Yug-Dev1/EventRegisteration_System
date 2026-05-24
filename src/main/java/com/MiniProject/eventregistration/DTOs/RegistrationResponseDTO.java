package com.MiniProject.eventregistration.DTOs;

import com.MiniProject.eventregistration.entity.Enums.PaymentStatus;
import com.MiniProject.eventregistration.entity.Enums.RegistrationStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegistrationResponseDTO {

    private Long registrationId;

    private String eventTitle;

    private String ticketTierName;
    private Integer ticketCount;
    private Double totalAmount;

    private PaymentStatus paymentStatus;
    private RegistrationStatus status;

    private LocalDateTime registrationDate;

    private Map<String, Object> answers;
}