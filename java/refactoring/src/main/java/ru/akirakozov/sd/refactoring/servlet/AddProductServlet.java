package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.command.AddProductCommand;
import ru.akirakozov.sd.refactoring.command.Command;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class AddProductServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String name = request.getParameter("name");
        long price = Long.parseLong(request.getParameter("price"));
        Command command = new AddProductCommand(name, price);
        BaseServlet.processPostCommand(response, command);
    }
}
