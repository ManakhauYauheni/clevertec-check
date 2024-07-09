package ru.clevertec.check.strategy;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;

import java.math.BigDecimal;

public class WholesaleDiscountStrategy implements DiscountStrategy {
    @Override
    public BigDecimal calculateDiscount(Product product, int quantity, DiscountCard discountCard) {
        if (product.isWholesale() && quantity >= 5) {
            // Применяем оптовую скидку 10%
            return product.getPrice().multiply(BigDecimal.valueOf(quantity)).multiply(BigDecimal.valueOf(0.1));
        }
        return BigDecimal.ZERO;
    }
}
