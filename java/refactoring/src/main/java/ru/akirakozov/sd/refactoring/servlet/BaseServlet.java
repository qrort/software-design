package ru.akirakozov.sd.refactoring.servlet;

import ru.akirakozov.sd.refactoring.command.Command;
import ru.akirakozov.sd.refactoring.database.ProductTableManager;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;


public class BaseServlet extends HttpServlet {
    private final ProductTableManager productTableManager;

    BaseServlet(ProductTableManager manager) {
        productTableManager = manager;
    }

    protected void processGetCommand(HttpServletResponse response, Command command) {
        productTableManager.executeQueryStatement(
                command.getSqlQuery(productTableManager.getTableName()),
                rs -> {
                    try {
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
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
        );
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }

    protected void processPostCommand(HttpServletResponse response, Command command) throws IOException {
        productTableManager.executeUpdateStatement(command.getSqlQuery(productTableManager.getTableName()));
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("OK");
    }
}
