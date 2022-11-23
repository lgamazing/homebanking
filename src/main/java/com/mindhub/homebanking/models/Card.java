package com.mindhub.homebanking.models;


import net.minidev.json.annotate.JsonIgnore;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Card {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name= "cliente_card")
    private Cliente Clientee;

    private String cardHolder;

    private CardType type;

    private CardColor color;

    private String number;

    private int cvv;

    private LocalDate fromDate;

    private LocalDate thruDate;

    private Boolean isActive;

    public Card() {
    };

    public Card(String cardHolder, CardType type, CardColor color, String number, int cvv, LocalDate fromDate, LocalDate thruDate, Boolean isActive) {
        this.cardHolder = cardHolder;
        this.type = type;
        this.color = color;
        this.number = number;
        this.cvv = cvv;
        this.fromDate = fromDate;
        this.thruDate = thruDate;
        this.isActive = isActive;
    }

    public Long getId() {
        return Id;
    }

    public String getCardHolder() {
        return cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        cardHolder = cardHolder;
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        type = type;
    }

    public CardColor getColor() {
        return color;
    }

    public void setColor(CardColor color) {
        color = color;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        number = number;
    }

    public int getCvv() {
        return cvv;
    }

    public void setCvv(int cvv) {
        cvv = cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public void setThruDate(LocalDate thruDate) {
        this.thruDate = thruDate;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @JsonIgnore
    public Cliente getClientee() {
        return Clientee;
    }

    public void setClientee(Cliente clientee) {
        Clientee = clientee;
    }
}
