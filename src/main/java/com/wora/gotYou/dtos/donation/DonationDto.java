package com.wora.gotYou.dtos.donation;

import com.wora.gotYou.entities.enums.DonationStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonationDto {
    private Long id;
    private LocalDate donationDate;
    private Boolean anonymous;
    private DonationStatus donationStatus;
    private Long donorId;
    private Double amount;
}