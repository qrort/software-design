package ru.akirakozov.sd.refactoring.command;

public class CountCommand implements Command {
    @Override
    public String getSqlQuery() {
        return "SELECT COUNT(*) FROM PRODUCT";
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
