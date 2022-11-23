package com.mindhub.homebanking.models;


import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="account_id")
    private Account account;
    private TransactionType type;

    private double amount;

    private String description;

    private LocalDateTime creationDate;

    public Transaction(){

    };

    public Transaction(TransactionType type, double amount, String description, LocalDateTime creationDate) {
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.creationDate = creationDate;
    }

    public long getId() {
        return Id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    @Override
    public String toString() {
        return "Transaction{" +
                "Id=" + Id +
                ", type=" + type +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                '}';
    }
}
