package com.wora.gotYou.dtos.request;

import com.wora.gotYou.dtos.user.UserDto;
import com.wora.gotYou.entities.enums.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestDto {
    private Long id;
    private String title;
    private String description;
    private LocalDate requestDate;
    private String reason;
    private RequestStatus status;
    private Double amount;
    private UserDto student;
}