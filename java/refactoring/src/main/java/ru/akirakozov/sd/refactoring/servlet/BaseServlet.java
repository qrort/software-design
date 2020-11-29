package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.command.Command;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class BaseServlet {
    protected static void processGetCommand(HttpServletResponse response, Command command) {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                Statement stmt = c.createStatement();
                ResultSet rs = stmt.executeQuery(command.getSqlQuery());
                response.getWriter().println("<html><body>");
                if (command.pageHeader() != null) {
                    response.getWriter().println(command.pageHeader());
                }
                if (command.listAll()) {
                    while (rs.next()) {
                        String name = rs.getString("name");
                        int price = rs.getInt("price");
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
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected static void processPostCommand(HttpServletResponse response, Command command) throws IOException {
        try {
            try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
                String sql = command.getSqlQuery();
                Statement stmt = c.createStatement();
                stmt.executeUpdate(sql);
                stmt.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }
}
