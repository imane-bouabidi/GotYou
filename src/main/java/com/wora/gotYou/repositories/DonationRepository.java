package com.wora.gotYou.repositories;

import com.wora.gotYou.entities.Donation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonationRepository extends JpaRepository<Donation, Long> {
}
