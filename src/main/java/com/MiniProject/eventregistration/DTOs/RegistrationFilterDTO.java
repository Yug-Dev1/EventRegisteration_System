package com.MiniProject.eventregistration.DTOs;

import com.MiniProject.eventregistration.entity.Enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationFilterDTO {

    private PaymentStatus paymentStatus;

    private String ticketTierName;

    private LocalDate fromDate;

    private LocalDate toDate;
}
