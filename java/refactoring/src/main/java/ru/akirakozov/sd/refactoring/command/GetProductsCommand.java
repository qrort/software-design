package ru.akirakozov.sd.refactoring.command;

public class GetProductsCommand implements Command {
    @Override
    public String getSqlQuery() {
        return "SELECT * FROM TestProducts";
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
