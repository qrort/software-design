package ru.akirakozov.sd.refactoring.command;

public class MinCommand implements Command {
    @Override
    public String getSqlQuery() {
        return "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1";
    }

    @Override
    public boolean listAll() {
        return true;
    }

    @Override
    public String pageHeader() {
        return "<h1>Product with min price: </h1>";
    }
}
