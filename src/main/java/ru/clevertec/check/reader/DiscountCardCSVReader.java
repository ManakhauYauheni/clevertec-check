package ru.clevertec.check.reader;

import ru.clevertec.check.factory.DiscountCardFactory;
import ru.clevertec.check.model.DiscountCard;

public class DiscountCardCSVReader extends CSVReader<DiscountCard> {
    @Override
    protected DiscountCard createEntity(String[] values) {
        return DiscountCardFactory.createDiscountCard(
                Integer.parseInt(values[1]), Double.parseDouble(values[2])
        );
    }
}