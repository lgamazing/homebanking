package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.ClientLoan;
import com.mindhub.homebanking.models.Loan;

import java.util.ArrayList;
import java.util.List;

public class LoanDTO {


    private Long id;
    private String name;

    private double maxAmount;

    private List<Integer> payments  = new ArrayList<>();

    public LoanDTO(Loan loan){
        this.name = loan.getName();

        this.maxAmount = loan.getMaxAmount();

        this.payments = loan.getPayments();

        this.id = loan.getId();
    }

    public String getName() {
        return name;
    }

    public double getMaxAmount() {
        return maxAmount;
    }

    public List<Integer> getPayments() {
        return payments;
    }

    public Long getId() {
        return id;
    }
}
