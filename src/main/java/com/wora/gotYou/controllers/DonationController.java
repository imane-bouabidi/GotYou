package com.wora.gotYou.controllers;

import com.wora.gotYou.dtos.donation.DonationDto;
import com.wora.gotYou.dtos.donation.CreateDonationDto;
import com.wora.gotYou.dtos.donation.UpdateDonationDto;
import com.wora.gotYou.entities.Donation;
import com.wora.gotYou.repositories.DonationRepository;
import com.wora.gotYou.repositories.DonorRepository;
import com.wora.gotYou.repositories.RequestRepository;
import com.wora.gotYou.services.interfaces.DonationServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/donations")
@RequiredArgsConstructor
public class DonationController {

    private final DonationServiceInter donationService;
    private final RequestRepository requestRepository;
    private final DonorRepository donorRepository;
    private final DonationRepository donationRepository;

    @PostMapping
    public ResponseEntity<DonationDto> createDonation(@RequestBody CreateDonationDto dto) {
        DonationDto savedDonation = donationService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDonation);
    }

    @PostMapping("/create-checkout-session")
    public ResponseEntity<String> createCheckoutSession(@RequestParam Long requestId, @RequestParam Double amount) throws StripeException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:4200/success?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl("http://localhost:4200/cancel")
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setCurrency("usd")
                                                .setUnitAmount((long) (amount * 100))
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName("Donation for Request #" + requestId)
                                                                .build()
                                                )
                                                .build()
                                )
                                .build()
                )
                .putMetadata("requestId", String.valueOf(requestId))
                .putMetadata("username", username)
                .build();

        Session session = Session.create(params);
        return ResponseEntity.ok(session.getId());
    }

    @GetMapping("/success")
    public ResponseEntity<String> handleSuccess(@RequestParam("session_id") String sessionId) throws StripeException {
        Session session = Session.retrieve(sessionId);
        if ("paid".equals(session.getPaymentStatus())) {
            Long requestId = Long.valueOf(session.getMetadata().get("requestId"));
            String username = session.getMetadata().get("username");

            Donation donation = new Donation();
            donation.setAmount(session.getAmountTotal() / 100.0);
            donation.setStripePaymentId(session.getPaymentIntent());
            donation.setDonationDate(LocalDateTime.now());
            donation.setRequest(requestRepository.findById(requestId)
                    .orElseThrow(() -> new RuntimeException("Request not found")));
            donation.setDonor(donorRepository.findByUserName(username)
                    .orElseThrow(() -> new RuntimeException("Donor not found")));

            donationRepository.save(donation);
            return ResponseEntity.ok("Payment successful! Donation recorded.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Payment not completed.");
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