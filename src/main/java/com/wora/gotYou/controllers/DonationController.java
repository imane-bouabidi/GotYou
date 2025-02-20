package com.wora.gotYou.controllers;

import com.wora.gotYou.dtos.donation.DonationDto;
import com.wora.gotYou.dtos.donation.CreateDonationDto;
import com.wora.gotYou.dtos.donation.UpdateDonationDto;
import com.wora.gotYou.services.interfaces.DonationServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationServiceInter donationService;

    @PostMapping
    public ResponseEntity<DonationDto> createDonation(@RequestBody CreateDonationDto dto) {
        DonationDto savedDonation = donationService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDonation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonationDto> updateDonation(
            @PathVariable Long id,
            @RequestBody UpdateDonationDto dto
    ) {
        DonationDto updatedDonation = donationService.update(dto, id);
        return ResponseEntity.ok(updatedDonation);
    }

    @GetMapping
    public ResponseEntity<List<DonationDto>> getAllDonations() {
        List<DonationDto> donations = donationService.findAll();
        return ResponseEntity.ok(donations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationDto> getDonationById(@PathVariable Long id) {
        DonationDto donation = donationService.getDonationById(id);
        return ResponseEntity.ok(donation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonation(@PathVariable Long id) {
        donationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}