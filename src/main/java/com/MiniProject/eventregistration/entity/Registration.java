package com.MiniProject.eventregistration.entity;

import com.MiniProject.eventregistration.entity.Enums.PaymentStatus;
import com.MiniProject.eventregistration.entity.Enums.RegistrationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"user", "event"})
public class Registration {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which user registered
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Which event user registered for
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "event_id", nullable = false)
    private Event event;

    // Selected ticket tier (VIP / General / Backstage etc.)
    private String ticketTierName;

    // Number of tickets booked
    private Integer ticketCount;

    // Final calculated price
    private Double totalAmount;

    // Payment lifecycle
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus paymentStatus;

    // Registration lifecycle
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus status;

    // Timestamp
    private LocalDateTime registrationDate;
}