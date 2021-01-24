package ru.akirakozov.sd.refactoring.command;

public class AddProductCommand implements Command {
    private final String name;
    private final Long price;

    public AddProductCommand(String n, Long p) {
        name = n;
        price = p;
    }

    public String getName() {
        return name;
    }

    public Long getPrice() {
        return price;
    }


    @Override
    public String getSqlQuery() {
        return "INSERT INTO TestProducts " + "(NAME, PRICE) VALUES (\"" + name + "\"," + price + ")";
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
