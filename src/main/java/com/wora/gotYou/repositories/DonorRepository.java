package com.wora.gotYou.repositories;

import com.wora.gotYou.dtos.donor.DonorDto;
import com.wora.gotYou.entities.Donor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DonorRepository extends JpaRepository<Donor, Long> {

    Optional<Donor> findByUserName(String userName);
}
