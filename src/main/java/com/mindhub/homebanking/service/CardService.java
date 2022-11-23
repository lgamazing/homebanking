package com.mindhub.homebanking.service;

import com.mindhub.homebanking.models.Card;

public interface CardService {


    void saveCard (Card card);

    Card getCard(String Number);

    void deleteCard (Card card);

}
