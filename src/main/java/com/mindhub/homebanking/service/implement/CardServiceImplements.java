package com.mindhub.homebanking.service.implement;

import com.mindhub.homebanking.models.Card;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class CardServiceImplements implements CardService {


    @Autowired
    CardRepository cardRepository;

    @Override
    public void saveCard(Card card) {
        cardRepository.save(card);
    }


    @Override
    public Card getCard(String number){
        return cardRepository.findByNumber(number);
    }

    @Override
    public void deleteCard(Card card){
        cardRepository.delete(card);
    }

}
