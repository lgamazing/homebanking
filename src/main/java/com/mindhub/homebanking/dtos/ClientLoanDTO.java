package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import java.util.ArrayList;
import java.util.List;

public class ClientLoanDTO {

    private long Id;

    private long IdLoan;

    private String name;

    private Double Amount;

    private Integer payments;

    public ClientLoanDTO(ClientLoan clientloan) {
        this.Id = clientloan.getId();

        this.IdLoan = clientloan.getLoan().getId();

        this.name = clientloan.getLoan().getName();

        this.Amount = clientloan.getAmount();

        this.payments = clientloan.getPayments();
    }

    public long getId() {
        return Id;
    }

    public long getIdLoan() {
        return IdLoan;
    }

    public String getName() {
        return name;
    }

    public Double getAmount() {
        return Amount;
    }

    public Integer getPayments() {
        return payments;
    }
}
