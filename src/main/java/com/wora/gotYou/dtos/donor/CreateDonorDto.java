package com.wora.gotYou.dtos.donor;

import com.wora.gotYou.dtos.user.CreateUserDto;
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
public class CreateDonorDto extends CreateUserDto {

    @NotBlank(message = "Speciality must not be blank")
    @Size(max = 50, message = "Speciality must be at most 50 characters")
    private String speciality;

    @Size(max = 255, message = "Reason must be at most 255 characters")
    private String reason;

    @NotNull(message = "Donor type must not be null")
    private DonorType donorType;
}