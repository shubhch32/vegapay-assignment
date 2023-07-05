package com.vegapay.project.entities;

import com.vegapay.project.enums.LimitOfferStatus;
import com.vegapay.project.enums.LimitType;
import com.vegapay.project.exceptions.BadRequestDataException;
import jakarta.persistence.*;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Entity
@Table(name = "limit_offer")
@Slf4j
public class LimitOffer {

    @Id
    @GeneratedValue
    private Integer limitOfferId;

    @ManyToOne
    private Account account;
    @Column(name = "limit_type")
    private LimitType limitType;
    @Column(name = "new_limit")
    private double newLimit;
    @Column(name = "offer_activation_time")
    private LocalDateTime offerActivationTime;
    @Column(name = "offer_expiry_time")
    private LocalDateTime offerExpiryTime;
    @Column(name = "status")
    private LimitOfferStatus status = LimitOfferStatus.PENDING;

    public LimitOffer() {
    }

    public LimitOffer(LimitType limitType, double newLimit, String offerActivationTime, String offerExpiryTime, Account account) throws BadRequestDataException {
        this.account = account;
        this.limitType = limitType;
        this.newLimit = newLimit;
        try {
            this.offerActivationTime = LocalDateTime.parse(offerActivationTime);
            this.offerExpiryTime = LocalDateTime.parse(offerExpiryTime);
        } catch (Exception ex) {
            log.error("Error while parsing dates -  activate time: " + offerActivationTime + " , expiry time: " + offerExpiryTime, ex);
            throw new BadRequestDataException("Date should be in the format YYYY-MM-DDThh:mm:ss");
        }
        if (!this.offerActivationTime.isBefore(this.offerExpiryTime))
            throw new BadRequestDataException("Offer expiry time should be in future and after offer activation time");
    }

    public Integer getLimitOfferId() {
        return limitOfferId;
    }

    public void setLimitOfferId(Integer limitOfferId) {
        this.limitOfferId = limitOfferId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
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
        return "LimitOffer{" +
                "limitOfferId=" + limitOfferId +
                ", account=" + account +
                ", limitType=" + limitType +
                ", newLimit=" + newLimit +
                ", offerActivationTime=" + offerActivationTime +
                ", offerExpiryTime=" + offerExpiryTime +
                ", status=" + status +
                '}';
    }
}
