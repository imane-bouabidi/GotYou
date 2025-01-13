package com.wora.gotYou.entities;

import com.wora.gotYou.entities.enums.DonationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    protected LocalDate donationDate;

    @NotBlank(message = "Anonymous option must not be blank")
    protected Boolean anonymous;

    @NotBlank(message = "Statut must not be blank")
    @Enumerated(EnumType.STRING)
    protected DonationStatus donationStatus;

    @ManyToOne
    protected Donor donor;
}
