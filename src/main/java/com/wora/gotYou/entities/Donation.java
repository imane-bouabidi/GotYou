package com.wora.gotYou.entities;

import com.wora.gotYou.entities.enums.DonationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    @ManyToOne
    @JoinColumn(name = "donor_id")
    private Donor donor;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;

    private LocalDateTime donationDate;

    private String stripePaymentId;

    @NotBlank(message = "Statut must not be blank")
    @Enumerated(EnumType.STRING)
    protected DonationStatus donationStatus;
}
