package com.wora.gotYou.mappers;

import com.wora.gotYou.dtos.donationBox.CreateDonationBoxDto;
import com.wora.gotYou.dtos.donationBox.UpdateDonationBoxDto;
import com.wora.gotYou.entities.DonationBox;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DonationBoxMapper {
    UpdateDonationBoxDto toDTO(DonationBox entity);
    DonationBox toEntity(CreateDonationBoxDto dto);
    DonationBox toEntity(UpdateDonationBoxDto updateDto);
}
