package ru.clevertec.check.service;

import ru.clevertec.check.reader.CSVReader;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.reader.ProductCSVReader;

import java.util.Map;

public class ProductService {
    private Map<Integer, Product> products;

    public ProductService(String productFilePath) {
        products = new ProductCSVReader().read(productFilePath);
    }

    public Product getProductById(int id) {
        return products.get(id);
    }
}