package com.vegapay.project.controllers;

import com.vegapay.project.dto.LimitOfferDto;
import com.vegapay.project.enums.LimitOfferStatus;
import com.vegapay.project.enums.LimitType;
import com.vegapay.project.exceptions.BadRequestDataException;
import com.vegapay.project.exceptions.InternalServerException;
import com.vegapay.project.exceptions.LimitReductionException;
import com.vegapay.project.service.LimitOfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

import static com.vegapay.project.constants.RestConstants.*;

@RestController
@RequestMapping(API_PATH + VERSION_1 + LIMIT_OFFER)
public class LimitOfferController {

    @Autowired
    LimitOfferService limitOfferService;

    @PostMapping
    public ResponseEntity<?> createLimitOffer(@RequestParam int accountId,
                                              @RequestParam LimitType limitType,
                                              @RequestParam double newLimit,
                                              @RequestParam String offerActivationTime,
                                              @RequestParam String offerExpiryTime) {
        int offerId;
        try {
            offerId = limitOfferService.createLimitOffer(accountId, limitType, newLimit, offerActivationTime, offerExpiryTime);
        } catch (BadRequestDataException | LimitReductionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (InternalServerException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(Collections.singletonMap("Offer Id", offerId));
    }

    @GetMapping(ACTIVE_LIMIT_OFFER)
    public ResponseEntity<?> listActiveLimitOffers(@RequestParam int accountId,
                                                   @RequestParam(required = false) String activeDate) {
        List<LimitOfferDto> limitOfferDtos;
        try {
            limitOfferDtos = limitOfferService.getActiveLimitOffers(accountId, activeDate);
        } catch (BadRequestDataException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (InternalServerException e) {
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(limitOfferDtos);
    }

    @PutMapping(STATUS)
    public ResponseEntity<?> updateLimitOfferStatus(@RequestParam int limitOfferId,
                                                    @RequestParam LimitOfferStatus status) {
        try {
            limitOfferService.updateLimitOfferStatus(limitOfferId, status);
        } catch (BadRequestDataException | LimitReductionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Status updated");
    }

}
