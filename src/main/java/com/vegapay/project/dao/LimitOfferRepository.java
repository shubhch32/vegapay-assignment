package com.vegapay.project.dao;

import com.vegapay.project.entities.LimitOffer;
import com.vegapay.project.enums.LimitOfferStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface LimitOfferRepository extends JpaRepository<LimitOffer, Integer> {

    Optional<LimitOffer> findByLimitOfferId(int limitOfferId);

    List<LimitOffer> findAllByAccountAccountIdAndStatus(int accountId, LimitOfferStatus status);

    List<LimitOffer> findAllByAccountAccountIdAndStatusAndOfferActivationTimeLessThanEqualAndOfferExpiryTimeGreaterThanEqual(int accountId,
                                                                                                                      LimitOfferStatus status,
                                                                                                                      LocalDateTime activeDate1,
                                                                                                                      LocalDateTime activeDate2);

}
