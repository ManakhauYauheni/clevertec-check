package ru.clevertec.check.service;

import ru.clevertec.check.reader.CSVReader;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.reader.DiscountCardCSVReader;

import java.util.HashMap;
import java.util.Map;

public class DiscountService {
    private Map<Integer, DiscountCard> discountCards;

    public DiscountService(String discountCardFilePath) {
        discountCards = new DiscountCardCSVReader().read(discountCardFilePath);
    }

    public DiscountCard getDiscountCard(int cardNumber) {
        return discountCards.values().stream()
                .filter(card -> card.getCardNumber() == cardNumber)
                .findFirst()
                .orElse(null);
    }
}