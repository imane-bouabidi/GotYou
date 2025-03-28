package com.wora.gotYou.entities;

import com.wora.gotYou.entities.enums.Gender;
import com.wora.gotYou.entities.enums.Level;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
@DiscriminatorValue("STUDENT")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Student extends User {

    @NotBlank(message = "Major must not be blank")
    @Size(max = 50, message = "Major must be at most 50 characters")
    private String major;

    @Column(length = 1000)
    @Size(max = 1000, message = "Situation details must be at most 1000 characters")
    private String situationDetails;

    @Column(length = 500)
    @NotBlank(message = "Situation title must not be blank")
    @Size(max = 500, message = "Situation title must be at most 100 characters")
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

    @OneToMany(mappedBy = "student")
    private List<Request> requests;

    @OneToMany(mappedBy = "student")
    private List<Stories> stories;

//    @OneToMany(mappedBy = "story")
//    private List<Media> media;

}
