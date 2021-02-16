package ru.akirakozov.sd.refactoring;

public class Product {
    private final String name;
    private final Long price;
    public Product(String n, Long p) {
        name = n;
        price = p;
    }

    public String getName() {
        return name;
    }
    public Long getPrice() {
        return price;
    }
}
