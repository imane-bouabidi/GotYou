package com.wora.gotYou.services.implementation;

import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.wora.gotYou.config.StripeService;
import com.wora.gotYou.dtos.donation.CreateDonationDto;
import com.wora.gotYou.dtos.donation.UpdateDonationDto;
import com.wora.gotYou.dtos.donation.DonationDto;
import com.wora.gotYou.entities.Donation;
import com.wora.gotYou.mappers.DonationMapper;
import com.wora.gotYou.repositories.DonationRepository;
import com.wora.gotYou.services.interfaces.DonationServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonationServiceImpl implements DonationServiceInter {

    private final DonationRepository donationRepository;
    private final DonationMapper donationMapper;
    private final StripeService stripeService;

    @Override
    public DonationDto save(CreateDonationDto dto) {
        Donation donation = donationMapper.toEntity(dto);
        Donation savedDonation = donationRepository.save(donation);
        return donationMapper.toDTO(savedDonation);
    }

    @Override
    public DonationDto update(UpdateDonationDto dto, Long id) {
        Donation existingDonation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found"));
        donationMapper.updateDonationFromDto(dto, existingDonation);
        Donation updatedDonation = donationRepository.save(existingDonation);
        return donationMapper.toDTO(updatedDonation);
    }

    @Override
    public List<DonationDto> findAll() {
        return donationRepository.findAll()
                .stream()
                .map(donationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public DonationDto getDonationById(Long id) {
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Donation not found with id: " + id));
        return donationMapper.toDTO(donation);
    }

//    private String processPayment(Double amount) throws StripeException {
//        if (amount > 0) {
//            PaymentIntent paymentIntent = stripeService.processPayment(amount);
//            log.info("Stripe Payment Successful: {}", paymentIntent.getId());
//            return paymentIntent.getClientSecret();
//        }
//        return null;
//    }

    @Override
    public void delete(Long id) {
        donationRepository.deleteById(id);
    }
}