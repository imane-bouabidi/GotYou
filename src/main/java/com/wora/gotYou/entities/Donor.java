package com.wora.gotYou.entities;

import com.wora.gotYou.entities.enums.DonorType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Donor extends User {

    @NotBlank(message = "Speciality must not be blank")
    @Size(max = 50, message = "Speciality must be at most 50 characters")
    private String speciality;

    @Size(max = 255, message = "Reason must be at most 255 characters")
    private String reason;

    @NotNull(message = "Type must not be null")
    @Enumerated(EnumType.STRING)
    private DonorType donorType;

    @OneToMany(mappedBy = "donor")
    private List<Donation> donations;
}
