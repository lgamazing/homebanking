package com.mindhub.homebanking.models;


import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Account {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "native")
    @GenericGenerator(name="native", strategy = "native")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="cliente_id") // nos permite personalizar la columna por la cual se va a ver representado. En este caso se esta adheriendo mediante la relacion.
    private Cliente cliente; // Llamamos al objeto en este caso de tipo "CLIENTE" y la relaciono.  //Lo necesito tener definido.

    @OneToMany(mappedBy = "account", fetch = FetchType.EAGER)
    Set<Transaction> Transactions = new HashSet<>();

    private String number;

    private LocalDateTime creationDate;

    private Boolean isActive;
    private Double balance;

    private AccountType type;



    public Account() {
    }

    public Account(String number, LocalDateTime creationDate, Double balance, AccountType type, Boolean isActive) {
        this.number = number;
        this.creationDate = creationDate;
        this.balance = balance;
        this.type = type;
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }


    public AccountType getType() {
        return type;
    }

    public void setType(AccountType type) {
        this.type = type;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }


    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }




    public Set<Transaction> getTransaction() {
        return Transactions;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setAccount(this);
        Transactions.add(transaction);
    }


}
