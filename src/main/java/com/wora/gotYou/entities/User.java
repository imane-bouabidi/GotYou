package com.wora.gotYou.entities;

import com.wora.gotYou.entities.enums.Role;
import com.wora.gotYou.entities.enums.UserStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static jakarta.persistence.InheritanceType.JOINED;

@Entity
@Inheritance(strategy = JOINED)
@DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "user_name", unique = true)
    @NotBlank(message = "The name must not be null")
    @Size(min = 5, max = 20, message = "User name must be between 5 and 20 characters")
    protected String userName;

    @NotBlank(message = "The name must not be null")
    @Size(min = 5, max = 20, message = "Name must be between 5 and 20 characters")
    protected String name;

    @NotBlank(message = "The last name must not be null")
    @Size(min = 5, max = 20, message = "Last name must be between 5 and 20 characters")
    protected String lastName;

    @Column(unique = true)
    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be valid")
    protected String email;

//    @NotBlank(message = "The last name must not be null")
//    @Size(min = 5, max = 20, message = "Last name must be between 5 and 20 characters")
//    protected String address;

    @Column(unique = true)
    @NotBlank(message = "CIN must not be blank")
    @Pattern(regexp = "^[A-Za-z0-9]{5,10}$", message = "CIN must be alphanumeric and between 5 and 10 characters")
    protected String cin;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    protected String password;

    @NotNull(message = "Birth date must not be null")
    @Past(message = "Birth date must be in the past")
    protected LocalDate birthDate;

    protected LocalDate inscriptionDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "profile_image")
    private String profileImage;

    @PrePersist
    protected void onCreate() {
        inscriptionDate = LocalDate.now();
    }
}
