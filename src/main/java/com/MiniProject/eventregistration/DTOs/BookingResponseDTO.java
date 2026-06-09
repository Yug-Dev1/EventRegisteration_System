package com.MiniProject.eventregistration.DTOs;

import com.MiniProject.eventregistration.entity.Enums.PaymentStatus;
import com.MiniProject.eventregistration.entity.Enums.RegistrationStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class BookingResponseDTO {

    private Long registrationId;

    private String eventTitle;

    private String ticketTierName;

    private Integer ticketCount;

    private Double totalAmount;

    private PaymentStatus paymentStatus;

    private RegistrationStatus status;

    private LocalDateTime registrationDate;
}
