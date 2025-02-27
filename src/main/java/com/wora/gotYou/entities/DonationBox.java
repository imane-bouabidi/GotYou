package com.wora.gotYou.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DonationBox extends Donation {
    @NotNull(message = "Amount is required")
    @Min(value = 1, message = "Amount must be at least 1$")
    private Double amount;

    @OneToMany(mappedBy = "donationBox")
    @JsonIgnore
    private List<Request> requests;
}
