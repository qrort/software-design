package ru.akirakozov.sd.refactoring.command;

public class MaxCommand implements Command {
    @Override
    public String getSqlQuery(String tableName) {
        return "SELECT * FROM " + tableName + " ORDER BY PRICE DESC LIMIT 1";
    }

    @Override
    public boolean listAll() {
        return true;
    }

    @Override
    public String pageHeader() {
        return "<h1>Product with max price: </h1>";
    }
}
