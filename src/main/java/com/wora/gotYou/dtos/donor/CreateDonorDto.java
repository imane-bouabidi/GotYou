package com.wora.gotYou.dtos.donor;

import com.wora.gotYou.entities.enums.DonorType;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDonorDto {

    @NotBlank(message = "Name must not be blank")
    @Size(min = 5, max = 20, message = "Name must be between 5 and 20 characters")
    private String name;

    @NotBlank(message = "Last name must not be blank")
    @Size(min = 5, max = 20, message = "Last name must be between 5 and 20 characters")
    private String lastName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "CIN must not be blank")
    @Pattern(regexp = "^[A-Za-z0-9]{5,10}$", message = "CIN must be alphanumeric and between 5 and 10 characters")
    private String cin;

    @NotNull(message = "Birth date must not be null")
    @Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @NotBlank(message = "Speciality must not be blank")
    @Size(max = 50, message = "Speciality must be at most 50 characters")
    private String speciality;

    @Size(max = 255, message = "Reason must be at most 255 characters")
    private String reason;

    @NotNull(message = "Donor type must not be null")
    private DonorType donorType;
}