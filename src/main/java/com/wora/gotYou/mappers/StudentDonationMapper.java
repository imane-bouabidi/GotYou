package com.wora.gotYou.mappers;

import com.wora.gotYou.dtos.studentDonation.CreateStudentDonationDto;
import com.wora.gotYou.dtos.studentDonation.UpdateStudentDonationDto;
import com.wora.gotYou.entities.StudentDonation;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentDonationMapper {
    UpdateStudentDonationDto toDTO(StudentDonation entity);
    StudentDonation toEntity(CreateStudentDonationDto dto);
    StudentDonation toEntity(UpdateStudentDonationDto updateDto);
}
