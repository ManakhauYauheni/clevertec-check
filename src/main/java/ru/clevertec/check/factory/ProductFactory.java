package ru.clevertec.check.factory;

import ru.clevertec.check.model.Product;

import java.math.BigDecimal;

public class ProductFactory {
    public static Product createProduct(int id, String description , BigDecimal price, int quantityInStock, boolean isWholesale) {
        return new Product(id, description, price, quantityInStock, isWholesale);
    }
}