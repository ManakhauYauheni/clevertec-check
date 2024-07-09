package ru.clevertec.check.factory;

import ru.clevertec.check.model.DiscountCard;

public class DiscountCardFactory {
    public static DiscountCard createDiscountCard(int cardNumber, double discountRate) {
        return new DiscountCard(cardNumber, discountRate);
    }
}