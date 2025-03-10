package com.wora.gotYou.entities;

import com.wora.gotYou.entities.enums.RequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title cannot be empty")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Description cannot be empty")
    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    private LocalDate requestDate;

    @NotBlank(message = "Reason cannot be empty")
    @Size(max = 200, message = "Reason cannot exceed 200 characters")
    private String reason;

    @NotNull(message = "Status is required")
    private RequestStatus status;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount cannot be negative")
    private Double amount;

    @ManyToOne
    private Student student;

    @OneToMany(mappedBy = "request")
    private List<StudentDonation> studentDonations;

    @ManyToOne
    private DonationBox donationBox;

    @OneToMany(mappedBy = "request")
    private List<Media> media;

    @PrePersist
    protected void onCreate() {
        requestDate = LocalDate.now();
    }
}
