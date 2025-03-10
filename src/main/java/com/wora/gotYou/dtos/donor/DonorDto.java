package com.wora.gotYou.dtos.donor;

import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.entities.enums.DonorType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DonorDto extends UserDto {
    private String speciality;
    private String reason;
    private DonorType donorType;
}