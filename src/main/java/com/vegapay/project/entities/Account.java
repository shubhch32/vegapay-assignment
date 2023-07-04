package com.vegapay.project.entities;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

@Entity
@Table(name = "account",
        indexes = {
            @Index(name = "account_id_index",  columnList="account_id", unique = true)
        }
)
public class Account {
    @Column(name = "account_id")
    @Id
    @GeneratedValue
    private int accountId;
    @Column(name = "customer_id")
    private int customerId;
    @Column(name = "account_limit")
    private double accountLimit;
    @Column(name = "per_transaction_limit")
    private double perTransactionLimit;
    @Column(name = "last_account_limit")
    private double lastAccountLimit;
    @Column(name = "last_per_transaction_limit")
    private double lastPerTransactionLimit;
    @Column(name = "account_limit_update_time")
    private LocalDateTime accountLimitUpdateTime;
    @Column(name = "per_transaction_limit_update_time")
    private LocalDateTime perTransactionLimitUpdateTime;

    public Account() {
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public double getAccountLimit() {
        return accountLimit;
    }

    public void setAccountLimit(double accountLimit) {
        this.accountLimit = accountLimit;
    }

    public double getPerTransactionLimit() {
        return perTransactionLimit;
    }

    public void setPerTransactionLimit(double perTransactionLimit) {
        this.perTransactionLimit = perTransactionLimit;
    }

    public double getLastAccountLimit() {
        return lastAccountLimit;
    }

    public void setLastAccountLimit(double lastAccountLimit) {
        this.lastAccountLimit = lastAccountLimit;
    }

    public double getLastPerTransactionLimit() {
        return lastPerTransactionLimit;
    }

    public void setLastPerTransactionLimit(double lastPerTransactionLimit) {
        this.lastPerTransactionLimit = lastPerTransactionLimit;
    }

    public LocalDateTime getAccountLimitUpdateTime() {
        return accountLimitUpdateTime;
    }

    public void setAccountLimitUpdateTime(LocalDateTime accountLimitUpdateTime) {
        this.accountLimitUpdateTime = accountLimitUpdateTime;
    }

    public LocalDateTime getPerTransactionLimitUpdateTime() {
        return perTransactionLimitUpdateTime;
    }

    public void setPerTransactionLimitUpdateTime(LocalDateTime perTransactionLimitUpdateTime) {
        this.perTransactionLimitUpdateTime = perTransactionLimitUpdateTime;
    }
}
