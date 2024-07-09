package ru.clevertec.check.strategy;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;

import java.math.BigDecimal;

public interface DiscountStrategy {
    BigDecimal calculateDiscount(Product product, int quantity, DiscountCard discountCard);
}
