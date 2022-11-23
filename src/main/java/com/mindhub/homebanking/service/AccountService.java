package com.mindhub.homebanking.service;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface AccountService {


    List<AccountDTO> getListAccountsDTO();

    AccountDTO getAccountDTO(Long id);

    Account findBynumber(String number);

    List<Account> getAll();

    void deleteAccount (Account account);




    void saveAccount (Account account);


}
