package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Cliente;
import com.mindhub.homebanking.repositories.ClienteRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountService accountService;

    @Autowired
    private ClientService clientService;


    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    @PostMapping(path = "/clientes/current/accounts")
    public ResponseEntity<Object> createAccount(Authentication authentication, @RequestParam AccountType type) {

            Cliente clienteCurrent = clientService.findByemail(authentication.getName());






        if(clienteCurrent != null) {


            if (clienteCurrent.getAccounts().size() >= 6) {


                return new ResponseEntity<>("usted supero el numero de cuentas creadas.", HttpStatus.FORBIDDEN);


            }

            if(type == null){


                return new ResponseEntity<>("Tipo de cuenta vacio", HttpStatus.FORBIDDEN);


            }









                Account newAccount = new Account("VIN"+"-"+getRandomNumber(1, 100000000), LocalDateTime.now(), 0.0, type, true);


                clienteCurrent.addAccount(newAccount);


                accountService.saveAccount(newAccount);


                return new ResponseEntity<>(HttpStatus.CREATED);



            }



        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @PatchMapping("/clients/current/accounts/delete")
    public ResponseEntity<Object> deleteAccount(@RequestParam String number, Authentication au){


        Cliente Autentic = clientService.getClientAuthentic(au);

        Account deletedAccount = accountService.findBynumber(number);

        if(Autentic != null){
            if (deletedAccount.getBalance() > 0) {

                return new ResponseEntity<>("La cuenta posee un saldo mayor a $0", HttpStatus.FORBIDDEN);

            }

            deletedAccount.setActive(false);

            accountService.saveAccount(deletedAccount);

            return new ResponseEntity<>("Cuenta Eliminada", HttpStatus.OK);

        }


        return new ResponseEntity<>("Cliente no autenticado", HttpStatus.FORBIDDEN);

    }


    @RequestMapping("/accounts")
    public List<AccountDTO> getAccountsDTO(){
        return accountService.getListAccountsDTO();
    }

    @RequestMapping("/accounts/{id}")
    public AccountDTO getAccount(@PathVariable Long id){
        return accountService.getAccountDTO(id);
    }


    @GetMapping("/cliente/current/account")
    public List<AccountDTO> getAccount(Authentication authentication) {

        Cliente clienteCurren = clientService.getClientAuthentic(authentication);




        return clienteCurren.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toList());


    }
}
