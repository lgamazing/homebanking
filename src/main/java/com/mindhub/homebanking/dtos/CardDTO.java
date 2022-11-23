package com.mindhub.homebanking.dtos;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.models.CardColor;
import com.mindhub.homebanking.models.CardType;
import com.mindhub.homebanking.models.Cliente;

import java.time.LocalDate;
import java.util.Set;

public class CardDTO {

    private Long Id;

    private String CardHolder;

    private CardType Type;

    private CardColor Color;

    private String Number;

    private int Cvv;

    private LocalDate fromDate;

    private LocalDate thruDate;

    private Boolean isActive;


    public CardDTO(Card card){
        this.Id = card.getId();

        this.Type = card.getType();

        this.Color = card.getColor();

        this.Number = card.getNumber();

        this.Cvv = card.getCvv();

        this.fromDate = card.getFromDate();

        this.thruDate = card.getThruDate();

        this.CardHolder = card.getCardHolder();

        this.isActive = card.getActive();

    }

    public Long getId() {
        return Id;
    }

    public String getCardHolder() {
        return CardHolder;
    }

    public CardType getType() {
        return Type;
    }

    public CardColor getColor() {
        return Color;
    }

    public String getNumber() {
        return Number;
    }

    public int getCvv() {
        return Cvv;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public LocalDate getThruDate() {
        return thruDate;
    }

    public Boolean getActive() {
        return isActive;
    }
}
