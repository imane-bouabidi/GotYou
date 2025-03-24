package com.wora.gotYou.controllers;

import com.wora.gotYou.dtos.donation.DonationDto;
import com.wora.gotYou.dtos.donation.CreateDonationDto;
import com.wora.gotYou.dtos.donation.UpdateDonationDto;
import com.wora.gotYou.entities.Donation;
import com.wora.gotYou.entities.Donor;
import com.wora.gotYou.entities.Request;
import com.wora.gotYou.entities.enums.DonationStatus;
import com.wora.gotYou.repositories.DonationRepository;
import com.wora.gotYou.repositories.DonorRepository;
import com.wora.gotYou.repositories.RequestRepository;
import com.wora.gotYou.services.interfaces.DonationServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger logger = LoggerFactory.getLogger(DonationController.class);
    @PostMapping
    public ResponseEntity<DonationDto> createDonation(@RequestBody CreateDonationDto dto) {
        DonationDto savedDonation = donationService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDonation);
    }

    @PostMapping("/create-donation-and-checkout")
    public ResponseEntity<String> createDonationAndCheckout(@RequestParam Long requestId, @RequestParam Double amount) {
        try {
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
            return ResponseEntity.ok()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(session.getId());
        } catch (StripeException e) {
            logger.error("Erreur Stripe : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur Stripe : " + e.getMessage());
        }
    }

    @GetMapping("/success")
    public ResponseEntity<String> handleSuccess(@RequestParam("session_id") String sessionId) {
        try {
            Session session = Session.retrieve(sessionId);
            if ("paid".equals(session.getPaymentStatus())) {
                String username = session.getMetadata().get("username");
                Long requestId = Long.parseLong(session.getMetadata().get("requestId"));
                Double amount = session.getAmountTotal() / 100.0;

                Donation donation = new Donation();
                donation.setAmount(amount);
                donation.setDonationDate(LocalDateTime.now());
                donation.setDonationStatus(DonationStatus.DONE);
                donation.setDonor(donorRepository.findByUserName(username).orElseThrow());
                donation.setRequest(requestRepository.findById(requestId).orElseThrow());
                donation.setStripePaymentId(session.getPaymentIntent());

                donationRepository.save(donation);
                return ResponseEntity.ok("Payment successful! Donation recorded.");
            }
            return ResponseEntity.badRequest().body("Payment not completed.");
        } catch (StripeException e) {
            logger.error("Erreur Stripe dans /success : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erreur : " + e.getMessage());
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> handleCancel(@RequestParam("donation_id") Long donationId) {
        try {
            logger.info("Paiement annulé pour donationId={}", donationId);
            Donation donation = donationRepository.findById(donationId)
                    .orElseThrow(() -> new RuntimeException("Donation non trouvée"));
            donation.setDonationStatus(DonationStatus.FAILED);
            donationRepository.save(donation);
            return ResponseEntity.ok("Paiement annulé.");
        } catch (Exception e) {
            logger.error("Erreur lors de l'annulation : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors du traitement de l'annulation.");
        }
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