package com.wora.gotYou.repositories;

import com.wora.gotYou.dtos.donation.DonationDto;
import com.wora.gotYou.entities.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findByDonorId(Long donorId);
    @Query("SELECT SUM(d.amount) FROM Donation d WHERE d.request.id = :requestId AND d.donationStatus = 'COMPLETED'")
    Double findTotalAmountByRequestId(@Param("requestId") Long requestId);

    @Query("SELECT d FROM Donation d WHERE d.request.id = :requestId AND d.donationStatus = 'COMPLETED' ORDER BY d.donationDate DESC")
    List<Donation> findTopByRequestIdOrderByDateDesc(@Param("requestId") Long requestId, Pageable pageable);

//    @Query("SELECT new com.wora.gotYou.dtos.donation.DonationDto(" +
//            "d.donor.id, " +
//            "d.donor.userName, " +
//            "d.amount, " +
//            "d.donationDate) " +
//            "FROM Donation d " +
//            "WHERE d.request.id = :requestId " +
//            "ORDER BY d.donationDate DESC")
//    List<DonationDto> findDonationDtosByRequestId(@Param("requestId") Long requestId);
}
