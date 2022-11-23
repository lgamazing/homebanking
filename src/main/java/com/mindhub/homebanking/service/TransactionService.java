package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Transaction;

import java.util.List;

public interface TransactionService {



    List<TransactionDTO> getTransactionsDTO();

    void saveTransaction (Transaction transaction);


}
