package com.wora.gotYou.dtos.donor;

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
public class DonorDto {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String password;
    private String cin;
    private LocalDate birthDate;
    private String speciality;
    private String reason;
    private DonorType donorType;
}