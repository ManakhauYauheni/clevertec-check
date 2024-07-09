package ru.clevertec.check.reader;

import ru.clevertec.check.factory.DiscountCardFactory;
import ru.clevertec.check.factory.ProductFactory;
import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

//чтение аргументов из командной строки (продуктвов, карты)
public abstract class CSVReader<T> {
    public Map<Integer, T> read(String filePath) {
        Map<Integer, T> data = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            boolean isFirstLine = true;
            while ((line = br.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] values = line.split(";");
                data.put(Integer.parseInt(values[0]), createEntity(values));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }

    protected abstract T createEntity(String[] values);
}