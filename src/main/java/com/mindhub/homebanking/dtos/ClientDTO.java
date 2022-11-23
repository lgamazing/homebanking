package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Cliente;
import com.mindhub.homebanking.models.Transaction;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class ClientDTO { //"DTO" o Data transferer Object, Son clases que reciben y transportan datos, Mostrando los datos como queremos que se muestren y no se genere recursividad.
    private long id;
    private String primerNombre;
    private String apellido;
    private String email;
    private Set<AccountDTO> accounts;

    private Set<ClientLoanDTO> Loans;

    private Set<CardDTO> Cards;
    public ClientDTO(Cliente cliente) {

        this.id = cliente.getId();

        this.primerNombre = cliente.getPrimerNombre();

        this.apellido = cliente.getApellido();

        this.email = cliente.getEmail();

        this.accounts = cliente.getAccounts().stream().map(account -> new AccountDTO(account)).collect(Collectors.toSet());

        this.Loans = cliente.getLoans().stream().map(loans -> new ClientLoanDTO(loans)).collect(Collectors.toSet());

        this.Cards = cliente.getCards().stream().map(card -> new CardDTO(card)).collect(Collectors.toSet());

    }

    public long getId() {
        return id;
    }

    public String getPrimerNombre() {
        return primerNombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public Set<AccountDTO> getAccounts() {
        return accounts;
    }

    public Set<ClientLoanDTO> getLoans() {
        return Loans;
    }

    public Set<CardDTO> getCard() {
        return Cards;
    }
}
