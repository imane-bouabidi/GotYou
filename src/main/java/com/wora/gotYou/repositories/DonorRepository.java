package com.wora.gotYou.repositories;

import com.wora.gotYou.entities.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DonorRepository extends JpaRepository<Donor, Integer> {
}
