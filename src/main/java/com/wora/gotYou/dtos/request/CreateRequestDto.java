package com.wora.gotYou.dtos.request;

import com.wora.gotYou.entities.enums.RequestStatus;
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
public class CreateRequestDto {

    @NotBlank(message = "Title must not be blank")
    @Size(max = 100, message = "Title must be at most 100 characters")
    private String title;

    @NotBlank(message = "Description must not be blank")
    @Size(max = 255, message = "Description must be at most 255 characters")
    private String description;

    private LocalDate requestDate;

    @NotBlank(message = "Reason must not be blank")
    @Size(max = 200, message = "Reason must be at most 200 characters")
    private String reason;

    @NotNull(message = "Status must not be null")
    private RequestStatus status;

    @NotNull(message = "Amount must not be null")
    @Min(value = 0, message = "Amount cannot be negative")
    private Double amount;

    @NotNull(message = "Student ID must not be null")
    private Long studentId;
}