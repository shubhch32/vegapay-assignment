package com.vegapay.project.dto;

import com.vegapay.project.entities.LimitOffer;
import com.vegapay.project.enums.LimitOfferStatus;
import com.vegapay.project.enums.LimitType;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public class LimitOfferDto {

    private int limitOfferId;
    @NonNull
    private int accountId;
    private LimitType limitType;
    private double newLimit;
    private LocalDateTime offerActivationTime;
    private LocalDateTime offerExpiryTime;
    private LimitOfferStatus status = LimitOfferStatus.PENDING;

    public LimitOfferDto() {
    }

    public Integer getLimitOfferId() {
        return limitOfferId;
    }

    public void setLimitOfferId(int limitOfferId) {
        this.limitOfferId = limitOfferId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public LimitType getLimitType() {
        return limitType;
    }

    public void setLimitType(LimitType limitType) {
        this.limitType = limitType;
    }

    public double getNewLimit() {
        return newLimit;
    }

    public void setNewLimit(double newLimit) {
        this.newLimit = newLimit;
    }

    public LocalDateTime getOfferActivationTime() {
        return offerActivationTime;
    }

    public void setOfferActivationTime(LocalDateTime offerActivationTime) {
        this.offerActivationTime = offerActivationTime;
    }

    public LocalDateTime getOfferExpiryTime() {
        return offerExpiryTime;
    }

    public void setOfferExpiryTime(LocalDateTime offerExpiryTime) {
        this.offerExpiryTime = offerExpiryTime;
    }

    public LimitOfferStatus getStatus() {
        return status;
    }

    public void setStatus(LimitOfferStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LimitOfferDto{" +
                "limitOfferId=" + limitOfferId +
                ", accountId=" + accountId +
                ", limitType=" + limitType +
                ", newLimit=" + newLimit +
                ", offerActivationTime=" + offerActivationTime +
                ", offerExpiryTime=" + offerExpiryTime +
                ", status=" + status +
                '}';
    }

    public static LimitOfferDto convertFromEntityToDto(LimitOffer limitOffer) {
        LimitOfferDto limitOfferDto = new LimitOfferDto();
        limitOfferDto.setLimitOfferId(limitOffer.getLimitOfferId());
        limitOfferDto.setLimitType(limitOffer.getLimitType());
        limitOfferDto.setNewLimit(limitOffer.getNewLimit());
        limitOfferDto.setOfferActivationTime(limitOffer.getOfferActivationTime());
        limitOfferDto.setOfferExpiryTime(limitOffer.getOfferExpiryTime());
        limitOfferDto.setStatus(limitOffer.getStatus());
        limitOfferDto.setAccountId(limitOffer.getAccount().getAccountId());
        return limitOfferDto;
    }
}

