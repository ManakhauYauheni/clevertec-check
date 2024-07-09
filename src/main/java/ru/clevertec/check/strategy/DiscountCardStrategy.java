package ru.clevertec.check.strategy;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;

import java.math.BigDecimal;

public class DiscountCardStrategy implements DiscountStrategy {
    @Override
    public BigDecimal calculateDiscount(Product product, int quantity, DiscountCard discountCard) {
        if (discountCard != null && (!product.isWholesale() || quantity < 5)) {
            // Применяем скидку по карте только если она предъявлена и товар не оптовый или количество меньше 5
            return product.getPrice().multiply(BigDecimal.valueOf(quantity)).multiply(BigDecimal.valueOf(discountCard.getDiscountRate() / 100.0));
        }
        return BigDecimal.ZERO;
    }
}
