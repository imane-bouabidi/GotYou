package com.wora.gotYou.services.interfaces;

import com.wora.gotYou.dtos.donation.CreateDonationDto;
import com.wora.gotYou.dtos.donation.UpdateDonationDto;
import com.wora.gotYou.dtos.donation.DonationDto;
import com.wora.gotYou.entities.Donation;
import com.wora.gotYou.services.GenericService;

import java.util.List;

public interface DonationServiceInter extends GenericService<CreateDonationDto, UpdateDonationDto, DonationDto, Long> {
    DonationDto getDonationById(Long id);
    List<DonationDto> findByDonorId(Long donorId);
    List<DonationDto> getDonationsByRequest(Long donorId);
}