package ru.akirakozov.sd.refactoring.command;

public interface Command {
    String getSqlQuery(String tableName);
    boolean listAll();
    String pageHeader();
}
