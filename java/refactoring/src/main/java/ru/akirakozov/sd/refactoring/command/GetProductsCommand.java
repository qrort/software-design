package ru.akirakozov.sd.refactoring.command;

public class GetProductsCommand implements Command {
    @Override
    public String getSqlQuery(String tableName) {
        return "SELECT * FROM " + tableName;
    }

    @Override
    public boolean listAll() {
        return true;
    }

    @Override
    public String pageHeader() {
        return null;
    }
}
