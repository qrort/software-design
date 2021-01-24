package ru.akirakozov.sd.refactoring.command;

public class CountCommand implements Command {
    @Override
    public String getSqlQuery() {
        return "SELECT COUNT(*) FROM TestProducts";
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
