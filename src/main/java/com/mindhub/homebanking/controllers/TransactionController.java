package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.dtos.TransactionDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Cliente;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClienteRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import com.mindhub.homebanking.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;



@RestController
@RequestMapping("/api")
public class TransactionController {


    @Autowired
    private TransactionService transactionService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;


    @Transactional
    @PostMapping("/transaction")
    public ResponseEntity<Object> createTransaction (Authentication authentication,
    @RequestParam double amount, @RequestParam String description, @RequestParam String accountO, @RequestParam String accountD) {

        Cliente clientCurrent = clientService.getClientAuthentic(authentication);
        Account accountOrigin = accountService.findBynumber(accountO);
        Account accountDestin = accountService.findBynumber(accountD);
        Set<Account> accountExist = clientCurrent.getAccounts().stream().filter(account -> account.getNumber().equals(accountO)).collect(Collectors.toSet());


        if(clientCurrent != null) {
            if (amount <= 0) {
                return new ResponseEntity<>("Falta el Monto", HttpStatus.EXPECTATION_FAILED);
            }

            if (description.isEmpty()) {
                return new ResponseEntity<>("Falta la descripcion", HttpStatus.FORBIDDEN);
            }
            if (accountO.isEmpty()) {
                return new ResponseEntity<>("Falta la cuenta origen", HttpStatus.FORBIDDEN);
            }
            if (accountD.isEmpty()) {
                return new ResponseEntity<>("Falta la cuenta destino", HttpStatus.FORBIDDEN);
            }

            if (accountO.equals(accountD)) {
                return new ResponseEntity<>("La cuenta origen no puede ser igual a la de destino", HttpStatus.FORBIDDEN);
            }

//        if (accountDestin == accountOrigin) {
//            return new ResponseEntity<>("Los numeros de cuentas son iguales", HttpStatus.BAD_REQUEST);
//        }

            if (accountExist.isEmpty()) {
                return new ResponseEntity<>("No existe tu cuenta", HttpStatus.FORBIDDEN);
            }

            if (accountDestin == null) {
                return new ResponseEntity<>("La cuenta de Destino No existe", HttpStatus.FORBIDDEN);
            }

            if (accountOrigin.getBalance() < amount) {
                return new ResponseEntity<>("La cuenta carece de plata", HttpStatus.FORBIDDEN);
            }


            Transaction transactionOrigin = new Transaction(TransactionType.DEBITO, amount, description + " Para " + accountDestin.getNumber(), LocalDateTime.now());
            Transaction transactionDestin = new Transaction(TransactionType.CREDITO, amount, description + " De " + accountOrigin.getNumber(), LocalDateTime.now());


            accountOrigin.addTransaction(transactionOrigin);
            accountDestin.addTransaction(transactionDestin);

            accountOrigin.setBalance(accountOrigin.getBalance() - amount);
            accountDestin.setBalance(accountDestin.getBalance() + amount);

            transactionService.saveTransaction(transactionOrigin);
            transactionService.saveTransaction(transactionDestin);

            accountService.saveAccount(accountOrigin);
            accountService.saveAccount(accountDestin);


            return new ResponseEntity<>(HttpStatus.CREATED);
        }


        return new ResponseEntity<>(HttpStatus.FORBIDDEN);



    }


    @RequestMapping("/transaction")
    public List<TransactionDTO> getTransactions() {
        return transactionService.getTransactionsDTO();
    }


}
