package com.mindhub.homebanking.models;


import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
public class ClientLoan {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private long id;


    private Double Amount;

    private Integer payments;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="client_id")
    private Cliente Client;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="loan_id")
    private Loan Loan;

    public ClientLoan() {
    }

    public ClientLoan(Double amount, Integer payments, Cliente client, Loan loan) {
        Amount = amount;
        this.payments = payments;
        Client = client;
        Loan = loan;
    }

    public long getId() {
        return id;
    }

    public Double getAmount() {
        return Amount;
    }

    public void setAmount(Double amount) {
        Amount = amount;
    }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public Cliente getClient() {
        return Client;
    }

    public void setClient(Cliente client) {
        Client = client;
    }

    @JsonIgnore
    public Loan getLoan() {
        return Loan;
    }

    public void setLoan(Loan loan) {
        Loan = loan;
    }


}
