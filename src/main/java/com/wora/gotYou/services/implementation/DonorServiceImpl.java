package com.wora.gotYou.services.implementation;

import com.wora.gotYou.dtos.donor.CreateDonorDto;
import com.wora.gotYou.dtos.donor.UpdateDonorDto;
import com.wora.gotYou.dtos.donor.DonorDto;
import com.wora.gotYou.entities.Donor;
import com.wora.gotYou.entities.enums.Role;
import com.wora.gotYou.entities.enums.UserStatus;
import com.wora.gotYou.mappers.DonorMapper;
import com.wora.gotYou.repositories.DonorRepository;
import com.wora.gotYou.services.interfaces.DonorServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonorServiceImpl implements DonorServiceInter {

    private final DonorRepository donorRepository;
    private final DonorMapper donorMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public DonorDto save(CreateDonorDto dto) {
        Donor donor = donorMapper.toEntity(dto);
        donor.setPassword(passwordEncoder.encode(dto.getPassword()));
        donor.setRole(Role.DONOR);
        donor.setStatus(UserStatus.PENDING);
        Donor savedDonor = donorRepository.save(donor);
        return donorMapper.toDTO(savedDonor);
    }

    @Override
    public DonorDto update(UpdateDonorDto dto, Long id) {
        Donor existingDonor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found"));
//        donorMapper.updateDonorFromDto(dto, existingDonor);
        Donor updatedDonor = donorRepository.save(existingDonor);
        return donorMapper.toDTO(updatedDonor);
    }

    public DonorDto getDonorById(Long id) {
        Donor donor = donorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donor not found with id: " + id));
        return donorMapper.toDTO(donor);
    }

    public DonorDto findByUserName(String userName) {
        Donor donor = donorRepository.findByUserName(userName)
                .orElseThrow(() -> new RuntimeException("Donor not found with user name: " + userName));
        return donorMapper.toDTO(donor);
    }

    @Override
    public List<DonorDto> findAll() {
        return donorRepository.findAll()
                .stream()
                .map(donorMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        donorRepository.deleteById(id);
    }
}