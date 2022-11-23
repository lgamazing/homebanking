package com.mindhub.homebanking.controllers;

import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.LoanApplicationDTO;
import com.mindhub.homebanking.dtos.LoanCreateDTO;
import com.mindhub.homebanking.dtos.LoanDTO;
import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import com.mindhub.homebanking.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class LoanController {



    @Autowired
    private LoanService loanService;

    @Autowired
    private ClientService clientService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private ClientLoanService clientLoanService;

    @Autowired
    private TransactionService transactionService;


    @GetMapping("/loan")
    public List<LoanDTO> getloans() {
        return loanService.getLoansDTO();
    }

    @Transactional //tareas
    @PostMapping("/loan")
    public ResponseEntity<Object> addLoan(Authentication authentication, @RequestBody LoanApplicationDTO loanApplicationDTO) {

        Cliente clietAutentic = clientService.getClientAuthentic(authentication);
        Account accountDestini = accountService.findBynumber(loanApplicationDTO.getAccountDestini());
        Loan loanExist = loanService.findByid(loanApplicationDTO.getIdLoan());

        if(clietAutentic != null){


            if(loanApplicationDTO.getAmount() <= 0){
                return new ResponseEntity<>("Monto negativo o 0", HttpStatus.FORBIDDEN);
            }
            if(loanApplicationDTO.getAccountDestini().isEmpty()){
                return new ResponseEntity<>("Cuenta desino vacio", HttpStatus.FORBIDDEN);
            }
            if(loanApplicationDTO.getPayments() == null){
                return new ResponseEntity<>("Pago vacio o 0", HttpStatus.FORBIDDEN);
            }
            if(loanApplicationDTO.getIdLoan() == null){
                return new ResponseEntity<>("Prestamo vacio", HttpStatus.FORBIDDEN);
            }



            if(loanExist == null){
                return new ResponseEntity<>("El prestamo no existe", HttpStatus.FORBIDDEN);
            }
            if(loanApplicationDTO.getAmount() > loanExist.getMaxAmount()){
                return new ResponseEntity<>("El monto solicitado excede el establecido", HttpStatus.FORBIDDEN);
            }
            if(!loanExist.getPayments().contains(loanApplicationDTO.getPayments())){
                return new ResponseEntity<>("Las cuotas no son las preestablecidas", HttpStatus.FORBIDDEN);
            }
            if(accountDestini == null){
                return new ResponseEntity<>("La cuenta destino no existe", HttpStatus.FORBIDDEN);
            }
            if(!clietAutentic.getAccounts().contains(accountDestini)){
                return new ResponseEntity<>("La cuenta   destino no es tuya", HttpStatus.FORBIDDEN);
            }
            if(clietAutentic.getLoans().stream().filter(clientLoan -> clientLoan.getLoan().getName().equals(loanExist.getName())).toArray().length == 1){
                return new ResponseEntity<>("La cuenta ya posee un prestamo solicitado", HttpStatus.FORBIDDEN);
            }


            if(loanApplicationDTO.getIdLoan() == 16){
                ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() * 1.2 , loanApplicationDTO.getPayments(), clietAutentic, loanExist);
                clientLoanService.saveClientLoan(clientLoan);
            }
            if(loanApplicationDTO.getIdLoan() == 17){
                ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() * 1.10 , loanApplicationDTO.getPayments(), clietAutentic, loanExist);
                clientLoanService.saveClientLoan(clientLoan);
            }
            if(loanApplicationDTO.getIdLoan() == 18){
                ClientLoan clientLoan = new ClientLoan(loanApplicationDTO.getAmount() * 1.15 , loanApplicationDTO.getPayments(), clietAutentic, loanExist);
                clientLoanService.saveClientLoan(clientLoan);
            }



            Transaction transactionCreated = new Transaction(TransactionType.CREDITO, loanApplicationDTO.getAmount(), loanExist.getName() +" " + "Prestamo Aprobado", LocalDateTime.now());

            accountDestini.addTransaction(transactionCreated);

            accountDestini.setBalance(accountDestini.getBalance() + loanApplicationDTO.getAmount());

            transactionService.saveTransaction(transactionCreated);

            accountService.saveAccount(accountDestini);




            return new ResponseEntity<>(HttpStatus.CREATED);
        }







        return new ResponseEntity<>(HttpStatus.FORBIDDEN);


    }

    @Transactional
    @PostMapping("/loan/create")
    public ResponseEntity<Object> createLoan(Authentication authentication, @RequestBody LoanCreateDTO loancCreate){

        Cliente clienteAdmin = clientService.getClientAuthentic(authentication);



        if (clienteAdmin != null){

            if(loancCreate.getName().isEmpty()){

                return new ResponseEntity<>("Nombre vacio", HttpStatus.FORBIDDEN);

            }

            if(loancCreate.getPayments().isEmpty()){

                return new ResponseEntity<>("payments vacios", HttpStatus.FORBIDDEN);

            }

            if(loancCreate.getMaxAmount() == 0){

                return new ResponseEntity<>("Monto bajo", HttpStatus.FORBIDDEN);

            }



            Loan loancreated = new Loan(loancCreate.getName(), loancCreate.getMaxAmount(), loancCreate.getPayments());

            loanService.saveLoan(loancreated);




















            return new ResponseEntity<>("Loan created", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Cliente no admitido", HttpStatus.FORBIDDEN);




    }




}
