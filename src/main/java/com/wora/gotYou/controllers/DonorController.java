package com.wora.gotYou.controllers;

import com.wora.gotYou.dtos.donor.DonorDto;
import com.wora.gotYou.dtos.donor.CreateDonorDto;
import com.wora.gotYou.dtos.donor.UpdateDonorDto;
import com.wora.gotYou.services.interfaces.DonorServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/donors")
@RequiredArgsConstructor
public class DonorController {

    private final DonorServiceInter donorService;

    @PostMapping
    public ResponseEntity<DonorDto> createDonor(@RequestBody CreateDonorDto dto) {
        DonorDto savedDonor = donorService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDonor);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DonorDto> updateDonor(
            @PathVariable Long id,
            @RequestBody UpdateDonorDto dto
    ) {
        DonorDto updatedDonor = donorService.update(dto, id);
        return ResponseEntity.ok(updatedDonor);
    }

    @GetMapping
    public ResponseEntity<List<DonorDto>> getAllDonors() {
        List<DonorDto> donors = donorService.findAll();
        return ResponseEntity.ok(donors);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonorDto> getDonorById(@PathVariable Long id) {
        DonorDto donor = donorService.getDonorById(id);
        return ResponseEntity.ok(donor);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDonor(@PathVariable Long id) {
        donorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}