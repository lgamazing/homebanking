package com.mindhub.homebanking.controllers;


import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.CardRepository;
import com.mindhub.homebanking.repositories.ClienteRepository;
import com.mindhub.homebanking.service.CardService;
import com.mindhub.homebanking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {

    @Autowired
    private CardService cardService;

    @Autowired
    private ClientService clientService;

    public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }


    @RequestMapping(path = "/clientes/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> createCard(Authentication authentication,
                                             @RequestParam CardType type, @RequestParam CardColor color
    ) {

        Cliente clienteCurrent = clientService.getClientAuthentic(authentication);

        Set<Card> cards = clienteCurrent.getCards().stream().filter(card -> card.getType() == type).collect(Collectors.toSet());
        Set<Card> cardsActive = cards.stream().filter(card -> card.getActive()).collect(Collectors.toSet());
        Set<Card> cardsCredit = cardsActive.stream().filter(card -> card.getType() == CardType.CREDITO).collect(Collectors.toSet());
        Set<Card> cardsDebit = cardsActive.stream().filter(card -> card.getType() == CardType.DEBITO).collect(Collectors.toSet());
        Set<Card> cardsCreditColor = cardsCredit.stream().filter(card -> card.getColor().equals(color)).collect(Collectors.toSet());
        Set<Card> cardsDebitColor = cardsDebit.stream().filter(card -> card.getColor().equals(color)).collect(Collectors.toSet());

        if (clienteCurrent != null) {


            if (color == null) {
                return new ResponseEntity<>("Falta el color", HttpStatus.FORBIDDEN);
            }

            if (type == null) {
                return new ResponseEntity<>("Falta el tipo", HttpStatus.FORBIDDEN);
            }

            if (cardsActive.size() == 3){
                return new ResponseEntity<>("No se pueden solicitar mas de este tipo de tarjeta: "+ type, HttpStatus.FORBIDDEN);
            }
            if (type.equals(CardType.CREDITO)){
                if (cardsCreditColor.size() == 1){
                    return new ResponseEntity<>("No se pueden solicitar mas tarjetas de este color: "+ color, HttpStatus.FORBIDDEN);
                }
            }
            if (type.equals(CardType.DEBITO)){
                if (cardsDebitColor.size() == 1){
                    return new ResponseEntity<>("No se pueden solicitar mas tarjetas de este color: "+ color, HttpStatus.FORBIDDEN);
                }
            }


            Card card = new Card(clienteCurrent.getPrimerNombre() + " " + clienteCurrent.getApellido(), type, color, (getRandomNumber(1000, 10000) + "-" + getRandomNumber(1000, 10000) + "-" + getRandomNumber(1000, 10000) + "-" + getRandomNumber(1000, 10000)), getRandomNumber(100, 1000), LocalDate.now(), LocalDate.now().plusYears(5), true);

            clienteCurrent.addCards(card);

            cardService.saveCard(card);

            return new ResponseEntity<>(HttpStatus.CREATED);


        }


        return new ResponseEntity<>(HttpStatus.NOT_FOUND);


    }


    @PatchMapping("/clients/current/cards/delete")
    public ResponseEntity<Object> deleteAccount(@RequestParam String number, Authentication authentication) {

        Cliente cliente = clientService.getClientAuthentic(authentication);

        Card deletedCard = cardService.getCard(number);

        if (cliente != null) {

            if (!cliente.getCards().contains(deletedCard)) {

                return new ResponseEntity<>("Carta no pertenece a cliente", HttpStatus.FORBIDDEN);

            }


            deletedCard.setActive(false);

            cardService.saveCard(deletedCard);

            return new ResponseEntity<>("Card deleted", HttpStatus.OK);


        }

        return new ResponseEntity<>("Cliente no encontrado", HttpStatus.FORBIDDEN);

    }

}


