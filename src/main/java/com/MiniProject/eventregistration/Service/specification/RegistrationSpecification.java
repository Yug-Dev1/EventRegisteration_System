package com.MiniProject.eventregistration.Service.specification;

import com.MiniProject.eventregistration.entity.Enums.PaymentStatus;
import com.MiniProject.eventregistration.entity.Enums.RegistrationStatus;
import com.MiniProject.eventregistration.entity.Registration;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class RegistrationSpecification {

    public static Specification<Registration> belongsToUser(Long userId) {
        return (root, query, cb) ->
                cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<Registration> hasPaymentStatus(
            PaymentStatus paymentStatus) {

        return (root, query, cb) ->
                paymentStatus == null
                        ? null
                        : cb.equal(root.get("paymentStatus"), paymentStatus);
    }

    public static Specification<Registration> hasTicketTier(
            String ticketTierName) {

        return (root, query, cb) ->
                ticketTierName == null || ticketTierName.isBlank()
                        ? null
                        : cb.equal(root.get("ticketTierName"), ticketTierName);
    }

    public static Specification<Registration> registeredAfter(
            LocalDate fromDate) {

        return (root, query, cb) ->
                fromDate == null
                        ? null
                        : cb.greaterThanOrEqualTo(
                        root.get("registrationDate"),
                        fromDate.atStartOfDay()
                );
    }

    public static Specification<Registration> registeredBefore(
            LocalDate toDate) {

        return (root, query, cb) ->
                toDate == null
                        ? null
                        : cb.lessThanOrEqualTo(
                        root.get("registrationDate"),
                        toDate.atTime(23,59,59)
                );
    }
}
