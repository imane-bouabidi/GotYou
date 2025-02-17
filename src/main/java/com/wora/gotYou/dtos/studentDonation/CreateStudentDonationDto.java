package com.wora.gotYou.dtos.studentDonation;

import com.wora.gotYou.entities.enums.DonationStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentDonationDto {

    @NotNull(message = "Donation date must not be null")
    private LocalDate donationDate;

    @NotNull(message = "Anonymous option must not be null")
    private Boolean anonymous;

    @NotNull(message = "Donation status must not be null")
    private DonationStatus donationStatus;

    @NotNull(message = "Donor ID must not be null")
    private Long donorId;

    @NotNull(message = "Amount must not be null")
    private Double amount;

    @NotNull(message = "Request ID must not be null")
    private Long requestId;
}
