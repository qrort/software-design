package ru.akirakozov.sd.refactoring.command;

public interface Command {
    String getSqlQuery();
    boolean listAll();
    String pageHeader();
}
