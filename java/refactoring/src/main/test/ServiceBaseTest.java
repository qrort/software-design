import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UncheckedIOException;
import java.net.MalformedURLException;
import java.net.URI;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.akirakozov.sd.refactoring.command.AddProductCommand;
import ru.akirakozov.sd.refactoring.servlet.AddProductServlet;
import ru.akirakozov.sd.refactoring.servlet.GetProductsServlet;
import ru.akirakozov.sd.refactoring.servlet.QueryServlet;

import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class ServiceBaseTest {
    protected static final int PORT = 8081;
    protected static final String LOCALHOST = "http://localhost:" + PORT;
    protected static final String PRODUCT_TABLE_NAME = "TestProducts";
    protected static Server server;

    @BeforeEach
    protected void runServer() throws Exception {
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:test.db")) {
            String sql = "DROP TABLE IF EXISTS " + PRODUCT_TABLE_NAME + ";";
            Statement stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
            sql = "CREATE TABLE " + PRODUCT_TABLE_NAME +
                    " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " NAME           TEXT    NOT NULL, " +
                    " PRICE          INT     NOT NULL)";
            stmt = c.createStatement();
            stmt.executeUpdate(sql);
            stmt.close();
        }

        server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        server.start();
        context.addServlet(new ServletHolder(new AddProductServlet()), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet()), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet()), "/query");
    }

    @AfterEach
    protected void tearDown() throws Exception {
        server.stop();
    }

    protected void addProduct(AddProductCommand command) {
        try {
            HttpClient client = HttpClient.newHttpClient();
             {
                String uri = LOCALHOST + "/add-product?name=" + command.getName() + "&price=" + command.getPrice();
                HttpRequest request = HttpRequest.newBuilder().uri(URI.create(uri)).build();
                client.send(request, HttpResponse.BodyHandlers.ofString());
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private URL toUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException("Malformed url: $url");
        }
    }

    protected String getPathResponse(String path) {
        URL url = toUrl(LOCALHOST + "/" + path);
        try (BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder res = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                res.append(inputLine).append("\n");
            }
            return res.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
