package com.wora.gotYou.dtos.donation;

import com.wora.gotYou.entities.Request;
import com.wora.gotYou.entities.enums.DonationStatus;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDonationDto {

    private Request request;

    private LocalDateTime donationDate;

    private String stripePaymentId;

    @NotNull(message = "Donation status must not be null")
    private DonationStatus donationStatus;

    @NotNull(message = "Donor ID must not be null")
    private Long donorId;

    @NotNull(message = "Amount must not be null")
    private Double amount;
}