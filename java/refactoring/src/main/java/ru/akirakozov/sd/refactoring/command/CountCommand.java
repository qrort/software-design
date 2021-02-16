package ru.akirakozov.sd.refactoring.command;

public class CountCommand implements Command {
    @Override
    public String getSqlQuery(String tableName) {
        return "SELECT COUNT(*) FROM " + tableName;
    }

    @Override
    public boolean listAll() {
        return false;
    }

    @Override
    public String pageHeader() {
        return "Number of products: ";
    }
}
