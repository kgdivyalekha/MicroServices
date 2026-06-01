package com.bank.cards.service;

import com.bank.cards.dto.CardsDTO;

public interface ICardsService {
    void createCard(String mobileNumber);
    CardsDTO fetchCard(String mobileNumber);
    boolean updateCard(CardsDTO cardsDTO);
    boolean deleteCard(String mobileNumber);
}
