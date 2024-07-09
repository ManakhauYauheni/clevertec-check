package ru.clevertec.check.service;

import ru.clevertec.check.model.DiscountCard;
import ru.clevertec.check.model.Product;
import ru.clevertec.check.exception.BadRequestException;
import ru.clevertec.check.exception.InternalServerErrorException;
import ru.clevertec.check.exception.NotEnoughMoneyException;
import ru.clevertec.check.service.DiscountService;
import ru.clevertec.check.service.ProductService;
import ru.clevertec.check.strategy.DiscountCardStrategy;
import ru.clevertec.check.strategy.DiscountStrategy;
import ru.clevertec.check.strategy.WholesaleDiscountStrategy;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class CheckService {
    private ProductService productService;
    private DiscountService discountService;
    private List<DiscountStrategy> discountStrategies;

    public CheckService(ProductService productService, DiscountService discountService) {
        this.productService = productService;
        this.discountService = discountService;
        this.discountStrategies = new ArrayList<>();
        this.discountStrategies.add(new WholesaleDiscountStrategy());
    }

    public void generateReceipt(String[] args) {
        try {
            Map<Product, Integer> products = parseArguments(args);
            BigDecimal balanceDebitCard = getBalanceDebitCard(args);
            DiscountCard discountCard = getDiscountCard(args);

            generateAndPrintReceipt(products, balanceDebitCard, discountCard);
        } catch (BadRequestException | NotEnoughMoneyException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (InternalServerErrorException e) {
            System.err.println("INTERNAL SERVER ERROR: " + e.getMessage());
        }
    }

    private Map<Product, Integer> parseArguments(String[] args) {
        Map<Integer, Integer> productQuantities = new LinkedHashMap<>();
        for (String arg : args) {
            if (arg.contains("-")) {
                String[] parts = arg.split("-");
                int productId = Integer.parseInt(parts[0]);
                int quantity = Integer.parseInt(parts[1]);
                productQuantities.put(productId, productQuantities.getOrDefault(productId, 0) + quantity);
            }
        }

        Map<Product, Integer> products = new LinkedHashMap<>();
        for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();
            Product product = productService.getProductById(productId);
            if (product == null) {
                throw new BadRequestException("BAD REQUEST");
            }
            if (quantity > product.getQuantityInStock()) {
                throw new BadRequestException("BAD REQUEST");
            }
            products.put(product, quantity);
        }
        return products;
    }

    private BigDecimal getBalanceDebitCard(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("balanceDebitCard=")) {
                double balance = Double.parseDouble(arg.split("=")[1]);
                return BigDecimal.valueOf(balance);
            }
        }
        return BigDecimal.ZERO;
    }

    private DiscountCard getDiscountCard(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("discountCard=")) {
                int cardNumber = Integer.parseInt(arg.split("=")[1]);
                DiscountCard discountCard = discountService.getDiscountCard(cardNumber);
                if (discountCard == null) {
                    // Применяем скидку 2%, если карта не найдена
                    return new DiscountCard(cardNumber, 2.0);
                }
                return discountCard;
            }
        }
        return null;
    }

    private void generateAndPrintReceipt(Map<Product, Integer> products, BigDecimal balanceDebitCard, DiscountCard discountCard) {
        BigDecimal total = BigDecimal.ZERO;
        BigDecimal totalDiscount = BigDecimal.ZERO;
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy;HH:mm");
        String formattedDateTime = currentDateTime.format(formatter);

        StringBuilder receipt = new StringBuilder();
        receipt.append("Date;Time\n").append(formattedDateTime).append("\n\n");
        receipt.append("QTY;DESCRIPTION;PRICE;DISCOUNT;TOTAL\n");

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            BigDecimal productTotal = product.getPrice().multiply(BigDecimal.valueOf(quantity));
            BigDecimal productDiscount;

            if (product.isWholesale() && quantity >= 5) {
                // Применяем оптовую скидку
                productDiscount = new WholesaleDiscountStrategy().calculateDiscount(product, quantity, discountCard);
            } else {
                // Применяем скидку по дисконтной карте
                productDiscount = new DiscountCardStrategy().calculateDiscount(product, quantity, discountCard);
            }

            total = total.add(productTotal);
            totalDiscount = totalDiscount.add(productDiscount);

            receipt.append(String.format("%d;%s;%.2f%s;%.2f%s;%.2f%s\n",
                    quantity, product.getDescription(), product.getPrice(),
                    "$", productDiscount, "$", productTotal, "$"));
        }

        if (balanceDebitCard.compareTo(total.subtract(totalDiscount)) < 0) {
            throw new NotEnoughMoneyException("NOT ENOUGH MONEY");
        }

        if (discountCard != null) {
            receipt.append("\nDISCOUNT CARD;DISCOUNT PERCENTAGE\n");
            receipt.append(discountCard.getCardNumber()).append(";").append(discountCard.getDiscountRate()).append("%\n");
        }
        receipt.append("\nTOTAL PRICE;TOTAL DISCOUNT;TOTAL WITH DISCOUNT\n");
        receipt.append(String.format("%.2f%s;%.2f%s;%.2f%s\n",
                total, "$",
                totalDiscount, "$",
                total.subtract(totalDiscount), "$"));

        System.out.println(receipt.toString());

        // Запись в CSV
        String fileName = "result.csv";
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append(receipt);
        } catch (IOException e) {
            throw new InternalServerErrorException("INTERNAL SERVER ERROR ", e);
        }
    }
}