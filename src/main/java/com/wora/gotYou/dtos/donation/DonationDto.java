package com.wora.gotYou.dtos.donation;

import com.wora.gotYou.dtos.request.RequestDto;
import com.wora.gotYou.entities.Donation;
import com.wora.gotYou.entities.Request;
import com.wora.gotYou.entities.enums.DonationStatus;
import com.wora.gotYou.mappers.RequestMapper;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationDto {
    private Long id;
    private RequestDto request;

    private LocalDateTime donationDate;

    private String stripePaymentId;

    @NotNull(message = "Donation status must not be null")
    private DonationStatus donationStatus;

    @NotNull(message = "Donor ID must not be null")
    private Long donorId;

    @NotNull(message = "Amount must not be null")
    private Double amount;

//    public DonationDto(Donation donation) {
//        this.id = donation.getId();
//
//    }

//    public DonationDto(Donation donation) {
//        this.id = donation.getId();
//        this.amount = donation.getAmount();
//        this.donationDate = donation.getDonationDate();
//        this.donationStatus = donation.getDonationStatus();
//        this.donorId = donation.getDonor().getId();
//        this.request = RequestMapper donation.getRequest();
//        this.stripePaymentId = donation.getStripePaymentId();
//    }
}