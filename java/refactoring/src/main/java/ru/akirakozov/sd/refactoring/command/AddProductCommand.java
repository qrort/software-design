package ru.akirakozov.sd.refactoring.command;

import ru.akirakozov.sd.refactoring.Product;

public class AddProductCommand implements Command {
    private final Product product;

    public AddProductCommand(Product p) {
        product = p;
    }

    public Product getProduct() {
        return product;
    }

    @Override
    public String getSqlQuery(String tableName) {
        return "INSERT INTO " + tableName +
                "(NAME, PRICE) VALUES (\"" +
                product.getName() + "\"," +
                product.getPrice() + ")";
    }

    @Override
    public boolean listAll() {
        return false;
    }

    @Override
    public String pageHeader() {
        return null;
    }
}
