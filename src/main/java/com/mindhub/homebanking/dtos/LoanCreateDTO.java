package com.mindhub.homebanking.dtos;

import java.util.List;

public class LoanCreateDTO {


    private double maxAmount;

    private String name;

    private List<Integer> payments;


    public double getMaxAmount() {
        return maxAmount;
    }

    public String getName() {
        return name;
    }

    public List<Integer> getPayments() {
        return payments;
    }
}
