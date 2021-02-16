package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.command.GetProductsCommand;
import ru.akirakozov.sd.refactoring.database.ProductTableManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends BaseServlet {
    public GetProductsServlet(ProductTableManager manager) {
        super(manager);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        processGetCommand(response, new GetProductsCommand());
    }
}
