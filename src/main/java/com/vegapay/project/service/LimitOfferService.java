package com.vegapay.project.service;

import com.vegapay.project.dao.AccountRepository;
import com.vegapay.project.dao.LimitOfferRepository;
import com.vegapay.project.dto.LimitOfferDto;
import com.vegapay.project.entities.Account;
import com.vegapay.project.entities.LimitOffer;
import com.vegapay.project.enums.LimitOfferStatus;
import com.vegapay.project.enums.LimitType;
import com.vegapay.project.exceptions.BadRequestDataException;
import com.vegapay.project.exceptions.InternalServerException;
import com.vegapay.project.exceptions.LimitReductionException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class LimitOfferService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    LimitOfferRepository limitOfferRepository;

    public int createLimitOffer(int accountId, LimitType limitType, double newLimit, String offerActivationTime,
                                String offerExpiryTime) throws BadRequestDataException, InternalServerException, LimitReductionException {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        LimitOffer limitOffer;
        if (account.isEmpty()) {
            throw new BadRequestDataException("Account id: " + accountId + "does not exist");
        } else {
            limitOffer = createLimitOfferObject(limitType, newLimit, offerActivationTime, offerExpiryTime, account.get());
            verifyRequestDataLimits(limitOffer);
        }
        limitOffer = limitOfferRepository.save(limitOffer);
        return limitOffer.getLimitOfferId();
    }

    public List<LimitOfferDto> getActiveLimitOffers(int accountId, String activeDate) throws BadRequestDataException, InternalServerException {
        List<LimitOffer> limitOffers;
        if (StringUtils.isBlank(activeDate)) {
            limitOffers = limitOfferRepository.findAllByAccountAccountIdAndStatus(accountId, LimitOfferStatus.PENDING);
        } else {
            LocalDateTime filterDate;
            try {
                filterDate = LocalDateTime.parse(activeDate);
            } catch (Exception ex) {
                log.error("Error while parsing date: " + activeDate, ex);
                throw new BadRequestDataException("Date should be in the format yyyy-MM-dd");
            }
            limitOffers = limitOfferRepository.findAllByAccountAccountIdAndStatusAndOfferActivationTimeLessThanEqualAndOfferExpiryTimeGreaterThanEqual(
                    accountId, LimitOfferStatus.PENDING, filterDate, filterDate
            );
        }
        return limitOffers.stream().map(LimitOfferDto::convertFromEntityToDto).toList();
    }

    @Transactional
    public void updateLimitOfferStatus(int limitOfferId, LimitOfferStatus status) throws BadRequestDataException, LimitReductionException {
        if (!(status.equals(LimitOfferStatus.ACCEPTED) || status.equals(LimitOfferStatus.REJECTED))) {
            throw new BadRequestDataException("Status can be changed to ACCEPTED or REJECTED");
        }
        Optional<LimitOffer> limitOffer = limitOfferRepository.findByLimitOfferId(limitOfferId);
        if (limitOffer.isEmpty())
            throw new BadRequestDataException("No limit offer exists with id = " + limitOfferId);
        updateStatusAndAccount(limitOffer.get(), status);
    }

    public LimitOffer createLimitOfferObject(LimitType limitType, double newLimit, String offerActivationTime,
                                             String offerExpiryTime, Account account) throws BadRequestDataException {
        LimitOffer limitOffer = new LimitOffer();
        limitOffer.setAccount(account);
        limitOffer.setLimitType(limitType);
        limitOffer.setNewLimit(newLimit);
        LocalDateTime activationTime, expiryTime;
        try {
            activationTime = LocalDateTime.parse(offerActivationTime);
            expiryTime = LocalDateTime.parse(offerExpiryTime);
        } catch (Exception ex) {
            log.error("Error while parsing dates -  activate time: " + offerActivationTime + " , expiry time: " + offerExpiryTime, ex);
            throw new BadRequestDataException("Date should be in the format YYYY-MM-DDThh:mm:ss");
        }
        if (!activationTime.isBefore(expiryTime))
            throw new BadRequestDataException("Offer expiry time should be in future and after offer activation time");
        limitOffer.setOfferActivationTime(activationTime);
        limitOffer.setOfferExpiryTime(expiryTime);
        return limitOffer;
    }

    public void updateStatusAndAccount(LimitOffer limitOffer, LimitOfferStatus status) throws LimitReductionException {
        limitOffer.setStatus(status);
        if (status.equals(LimitOfferStatus.REJECTED)) {
            limitOfferRepository.save(limitOffer);
            return;
        }
        verifyRequestDataLimits(limitOffer);
        if (limitOffer.getLimitType().equals(LimitType.ACCOUNT_LIMIT)) {
            limitOffer.getAccount().setLastAccountLimit(limitOffer.getAccount().getAccountLimit());
            limitOffer.getAccount().setAccountLimit(limitOffer.getNewLimit());
            limitOffer.getAccount().setAccountLimitUpdateTime(LocalDateTime.now());
        } else {
            limitOffer.getAccount().setLastPerTransactionLimit(limitOffer.getAccount().getPerTransactionLimit());
            limitOffer.getAccount().setPerTransactionLimit(limitOffer.getNewLimit());
            limitOffer.getAccount().setPerTransactionLimitUpdateTime(LocalDateTime.now());
        }
        limitOffer.setAccount(accountRepository.save(limitOffer.getAccount()));
        limitOfferRepository.save(limitOffer);
    }

    public void verifyRequestDataLimits(LimitOffer limitOffer) throws LimitReductionException {
        if (limitOffer.getLimitType().equals(LimitType.ACCOUNT_LIMIT)) {
            if (limitOffer.getNewLimit() <= limitOffer.getAccount().getAccountLimit())
                throw new LimitReductionException("New limit for Account limit should be greater than the existing limit");
        } else {
            if (limitOffer.getNewLimit() <= limitOffer.getAccount().getPerTransactionLimit())
                throw new LimitReductionException("New limit for Per Transaction limit should be greater than the existing limit");
        }
    }
}
