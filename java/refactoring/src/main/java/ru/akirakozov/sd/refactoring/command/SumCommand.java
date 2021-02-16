package ru.akirakozov.sd.refactoring.command;

public class SumCommand implements Command{
    @Override
    public String getSqlQuery(String tableName) {
        return "SELECT SUM(price) FROM " + tableName;
    }

    @Override
    public boolean listAll() {
        return false;
    }

    @Override
    public String pageHeader() {
        return "Summary price: ";
    }
}
