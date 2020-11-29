package ru.akirakozov.sd.refactoring.servlet;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private static final Map<String, String> commandQuery = new HashMap<String, String>() {{
        put("max", "SELECT * FROM PRODUCT ORDER BY PRICE DESC LIMIT 1");
        put("min", "SELECT * FROM PRODUCT ORDER BY PRICE LIMIT 1");
        put("sum", "SELECT SUM(price) FROM PRODUCT");
        put("count", "SELECT COUNT(*) FROM PRODUCT");
    }};

    private static final Map<String, String> pageHeader = new HashMap<String, String>() {{
        put("max", "<h1>Product with max price: </h1>");
        put("min", "<h1>Product with min price: </h1>");
        put("sum", "Summary price: ");
        put("count", "Number of products: ");
    }};

    private static final Set<String> listAll = new HashSet<String>() {{
        add("max");
        add("min");
    }};

    private void process(HttpServletResponse response, String command) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(commandQuery.get(command));
                response.getWriter().println("<html><body>");
                response.getWriter().println(pageHeader.get(command));

                if (listAll.contains(command)) {
                    while (rs.next()) {
                        String  name = rs.getString("name");
                        int price  = rs.getInt("price");
                        response.getWriter().println(name + "\t" + price + "</br>");
                    }
                } else {
                    if (rs.next()) {
                        response.getWriter().println(rs.getInt(1));
                    }
                }
                response.getWriter().println("</body></html>");

                rs.close();
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");
        if (commandQuery.containsKey(command)) {
            process(response, command);
        } else {
            response.getWriter().println("Unknown command: " + command);
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
