package com.mindhub.homebanking.dtos;

import java.util.List;

public class LoanApplicationDTO {


        private Long idLoan;

        private double amount;

        private Integer payments;

        private String accountDestini;

        public Long getIdLoan() {
            return idLoan;
        }

        public double getAmount() {
            return amount;
        }

    public Integer getPayments() {
        return payments;
    }

    public void setPayments(Integer payments) {
        this.payments = payments;
    }

    public String getAccountDestini() {
            return accountDestini;
        }

    public void setIdLoan(Long idLoan) {
        this.idLoan = idLoan;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }



    public void setAccountDestini(String accountDestini) {
        this.accountDestini = accountDestini;
    }
}
