package com.wora.gotYou.dtos.student;

import com.wora.gotYou.dtos.user.CreateUserDto;
import com.wora.gotYou.entities.enums.Gender;
import com.wora.gotYou.entities.enums.Level;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentDto extends CreateUserDto {

    @NotBlank(message = "Major must not be blank")
    @Size(max = 50, message = "Major must be at most 50 characters")
    private String major;

    @Size(max = 255, message = "Situation details must be at most 255 characters")
    private String situationDetails;

    @NotBlank(message = "Situation title must not be blank")
    @Size(max = 100, message = "Situation title must be at most 100 characters")
    private String situationTitle;

    @NotNull(message = "Start date must not be null")
    @PastOrPresent(message = "Start date must be in the past or present")
    private LocalDate startDate;

    @NotNull(message = "Gender must not be null")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @NotNull(message = "Level must not be null")
    @Enumerated(EnumType.STRING)
    private Level level;
}
