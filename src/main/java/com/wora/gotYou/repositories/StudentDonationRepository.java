package com.wora.gotYou.repositories;

import com.wora.gotYou.entities.StudentDonation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentDonationRepository extends JpaRepository<StudentDonation, Integer> {
}
