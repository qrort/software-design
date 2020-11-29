package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.command.GetProductsCommand;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        BaseServlet.processGetCommand(response, new GetProductsCommand());
    }
}
