package ru.clevertec.check.model;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountCard {
    private int cardNumber;
    private double discountRate;
}