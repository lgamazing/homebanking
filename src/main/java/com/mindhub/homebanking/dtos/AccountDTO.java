package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public class AccountDTO {


    private long id;

    private String number;

    private LocalDateTime creationDate;

    private Double balance;

    private AccountType type;

    private Boolean isActive;

    private Set<TransactionDTO> transactions;


    public AccountDTO(Account account) {
        this.id = account.getId();

        this.number = account.getNumber();

        this.balance = account.getBalance();

        this.creationDate = account.getCreationDate();

        this.type = account.getType();

        this.isActive = account.getActive();

        this.transactions = account.getTransaction().stream().map(transaction -> new TransactionDTO(transaction)).collect(Collectors.toSet());
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Double getBalance() {
        return balance;
    }

    public AccountType getType() {
        return type;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Set<TransactionDTO> getTransactions() {
        return transactions;
    }
}
