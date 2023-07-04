package com.vegapay.project.entities;

import com.vegapay.project.enums.LimitOfferStatus;
import com.vegapay.project.enums.LimitType;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "limit_offer")
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
