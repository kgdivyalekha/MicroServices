package com.bank.cards.service.impl;

import com.bank.cards.constants.CardsConstants;
import com.bank.cards.dto.CardsDTO;
import com.bank.cards.entity.Cards;
import com.bank.cards.exception.CardAlreadyExistsException;
import com.bank.cards.exception.ResourceNotFoundException;
import com.bank.cards.mapper.CardsMapper;
import com.bank.cards.repository.CardsRepository;
import com.bank.cards.service.ICardsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class CardsServiceImpl implements ICardsService {
    private CardsRepository cardsRepository;
    @Override
    public void createCard(String mobileNumber) {
        Optional<Cards> optionalCards=cardsRepository.findByMobileNumber(mobileNumber);
        if(optionalCards.isPresent())
            throw new CardAlreadyExistsException("Card Already exists fot this mobile number : "+mobileNumber);
        cardsRepository.save(createNewCard(mobileNumber));
    }

    private Cards createNewCard(String mobileNumber) {
        Cards newCard=new Cards();
        long randomNumber=100000000000L+new Random().nextInt(900000000);
        newCard.setCardNumber(Long.toString(randomNumber));
        newCard.setMobileNumber(mobileNumber);
        newCard.setCardType(CardsConstants.CREDIT_CARD);
        newCard.setTotalLimit(CardsConstants.NEW_CARD_LIMIT);
        newCard.setAmountUsed(0);
        newCard.setAvailableAmount(CardsConstants.NEW_CARD_LIMIT);
        return newCard;
    }

    @Override
    public CardsDTO fetchCard(String mobileNumber) {
        Cards cards=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Card","Mobile Number",mobileNumber)
        );
        return CardsMapper.mapToCardsDTO(cards,new CardsDTO());
    }

    @Override
    public boolean updateCard(CardsDTO cardsDTO) {
        Cards cards=cardsRepository.findByCardNumber(cardsDTO.getCardNumber()).orElseThrow(
                ()-> new ResourceNotFoundException("Card","Card Number", cardsDTO.getCardNumber())
        );
        CardsMapper.mapToCards(cardsDTO,cards);
        cardsRepository.save(cards);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Cards cards=cardsRepository.findByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Card","Mobile Number",mobileNumber)
        );
        cardsRepository.deleteById(cards.getCardId());
        return true;
    }
}
