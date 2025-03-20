package com.wora.gotYou.dtos.user;

import com.wora.gotYou.entities.enums.Role;
import com.wora.gotYou.entities.enums.UserStatus;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserDto {

    @NotBlank(message = "The name must not be null")
    @Size(min = 5, max = 20, message = "User name must be between 5 and 20 characters")
    protected String userName;

    @NotBlank(message = "The name must not be null")
    @Size(min = 5, max = 20, message = "Name must be between 5 and 20 characters")
    protected String name;

    @NotBlank(message = "The last name must not be null")
    @Size(min = 5, max = 20, message = "Last name must be between 5 and 20 characters")
    protected String lastName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    protected String email;

    @NotBlank(message = "CIN must not be blank")
    @Pattern(regexp = "^[A-Za-z0-9]{5,10}$", message = "CIN must be alphanumeric and between 5 and 10 characters")
    protected String cin;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    protected String password;

    @NotNull(message = "Birth date must not be null")
    @Past(message = "Birth date must be in the past")
    protected String birthDate;
    private Role role;
    private UserStatus status;
    protected LocalDate inscriptionDate;

}
