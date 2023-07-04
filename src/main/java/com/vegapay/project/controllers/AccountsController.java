package com.vegapay.project.controllers;

import com.vegapay.project.dao.AccountRepository;
import com.vegapay.project.entities.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

import static com.vegapay.project.constants.ParamConstants.ACCOUNT_ID;
import static com.vegapay.project.constants.RestConstants.*;

@RestController
@RequestMapping(API_PATH + VERSION_1 + ACCOUNT)
@Slf4j
public class AccountsController {

    @Autowired
    AccountRepository accountRepository;

    @PostMapping
    public ResponseEntity<?> addAccount(@RequestBody Account account) {
        try {
            account = accountRepository.save(account);
        } catch (Exception ex) {
            log.error("Encountered error in saving account with account id = " + account.getAccountId(), ex);
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok(Collections.singletonMap("Account Id", account.getAccountId()));
    }

    @GetMapping
    public ResponseEntity<?> getAccount(@RequestParam(value = ACCOUNT_ID) int accountId) {
        Optional<Account> account = accountRepository.findByAccountId(accountId);
        if (account.isPresent())
            return ResponseEntity.ok(account.get());
        else
            return ResponseEntity.noContent().build();
    }

}
