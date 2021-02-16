package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.command.*;
import ru.akirakozov.sd.refactoring.database.ProductTableManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author akirakozov
 */
public class QueryServlet extends BaseServlet {
    private static final Map<String, Command> stringCommandMap = new HashMap<>() {{
        put("max", new MaxCommand());
        put("min", new MinCommand());
        put("sum", new SumCommand());
        put("count", new CountCommand());
    }};

    public QueryServlet(ProductTableManager manager) {
        super(manager);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        if (stringCommandMap.containsKey(command)) {
            processGetCommand(response, stringCommandMap.get(command));
        } else {
            response.getWriter().println("Unknown command: " + command);
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
