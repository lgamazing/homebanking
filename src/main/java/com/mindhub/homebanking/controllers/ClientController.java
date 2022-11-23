package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.dtos.AccountDTO;
import com.mindhub.homebanking.dtos.ClientDTO;
import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.AccountType;
import com.mindhub.homebanking.models.Cliente;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClienteRepository;
import com.mindhub.homebanking.service.AccountService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
//una api con las restricciones de rest, y apiRestFull es una api ya creado con las

@RestController // Restricciones de Rest: Escuchan y Responden Peticiones.
@RequestMapping("/api")
public class ClientController {

    @Autowired //Autowire inyección de dependencia en el controlador, crea una instancia.
    private ClientService clientService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AccountService accountService;

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    @RequestMapping(path = "/clientes", method = RequestMethod.POST)
    public ResponseEntity<Object> register(

            @RequestParam String primerNombre, @RequestParam String apellido,

            @RequestParam String email, @RequestParam String password ){



        if (primerNombre.isEmpty()) {
            return new ResponseEntity<>("Falta el nombre", HttpStatus.FORBIDDEN);
        }
        if (apellido.isEmpty()){
            return new ResponseEntity<>("Falta el Apellido", HttpStatus.FORBIDDEN);
        }
        if (email.isEmpty()){
            return new ResponseEntity<>("Falta el email", HttpStatus.FORBIDDEN);

        }
        if (password.isEmpty()){
            return new ResponseEntity<>("Falta la Contraseña", HttpStatus.FORBIDDEN);
        }

        if (clientService.findByemail(email) !=  null) {
            return new ResponseEntity<>("Email ya esta en Uso", HttpStatus.FORBIDDEN);
        }



        Cliente cliente = new Cliente(primerNombre, apellido, email, passwordEncoder.encode(password));

        clientService.saveClient(cliente);

        Account newAccount = new Account("VIN"+"-"+getRandomNumber(1, 100000000), LocalDateTime.now(), 0.0, AccountType.CORRIENTE, true);

        cliente.addAccount(newAccount);

        accountService.saveAccount(newAccount);

        return new ResponseEntity<>(HttpStatus.CREATED);

    }

//    @PatchMapping("/clientes/current")
//    public ResponseEntity<?> register(Authentication authentication, @RequestParam String password){
//        Cliente Cliente = clienteRepository.findByEmail(authentication.getName());
//
//        Cliente.setPassword(passwordEncoder.encode(password));
//        clienteRepository.save(Cliente);
//        return new ResponseEntity<>(HttpStatus.ACCEPTED);
//    }







    @RequestMapping("/clientes/current")
    public ClientDTO getClientAuthenic (Authentication authentication){
        return new ClientDTO(clientService.getClientAuthentic(authentication));
    }

    @RequestMapping("/clientes")//endpoint.
    public List<ClientDTO> getClientsDTO() {
        return clientService.getClientsDTO(); //Stream: Lo convierta a un nuevo tipo de colección y puedo usar metodos de orden superiror
    }
//findAll o FindById Heredando los metodos de JPA.
    @RequestMapping("/clientes/{id}")
        public ClientDTO getClient(@PathVariable Long id){ // Ruta Variable.
             return clientService.getClientDTO(id);//estoy asociando ese CLIENTE con un ClientDTO. OPCIONAL.
        }

}
