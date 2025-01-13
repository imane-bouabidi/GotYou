package com.wora.gotYou.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDonation extends Donation {
    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be at least 1$")
    private Double amount;

    @ManyToOne
    private Request request;
}
