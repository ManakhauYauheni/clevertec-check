package ru.clevertec.check.reader;

import ru.clevertec.check.factory.ProductFactory;
import ru.clevertec.check.model.Product;

import java.math.BigDecimal;

public class ProductCSVReader extends CSVReader<Product> {
    @Override
    protected Product createEntity(String[] values) {
        return ProductFactory.createProduct(
                Integer.parseInt(values[0]), values[1], new BigDecimal(values[2]),
                Integer.parseInt(values[3]), Boolean.parseBoolean(values[4])
        );
    }
}