package com.wora.gotYou.services.interfaces;

import com.wora.gotYou.dtos.donation.CreateDonationDto;
import com.wora.gotYou.dtos.donation.UpdateDonationDto;
import com.wora.gotYou.dtos.donation.DonationDto;
import com.wora.gotYou.services.GenericService;

public interface DonationServiceInter extends GenericService<CreateDonationDto, UpdateDonationDto, DonationDto, Long> {
    DonationDto getDonationById(Long id);
}