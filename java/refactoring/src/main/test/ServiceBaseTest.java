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
import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.command.AddProductCommand;
import ru.akirakozov.sd.refactoring.database.ProductTableManager;
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
import java.util.List;

public class ServiceBaseTest {
    protected static final int PORT = 8081;
    protected static final String LOCALHOST = "http://localhost:" + PORT;
    protected static final String PRODUCT_TABLE_NAME = "TestProducts";
    protected static Server server;
    protected static ProductTableManager productTableManager;

    Product XBOX = new Product("XBOX360", (long) 100500);
    Product PS4 = new Product("PS4", (long) 100499);
    Product KFCONSOLE = new Product("KFCONSOLE", (long) 3450);


    @BeforeEach
    protected void runServer() throws Exception {
        server = new Server(PORT);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        server.start();

        productTableManager = new ProductTableManager(PRODUCT_TABLE_NAME);
        context.addServlet(new ServletHolder(new AddProductServlet(productTableManager)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(productTableManager)), "/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(productTableManager)), "/query");
    }

    @AfterEach
    protected void tearDown() throws Exception {
        server.stop();
        productTableManager.dropProductTable();
    }

    protected void addProducts(List<?> list) {
        for (Object object : list) {
            if (object instanceof Product) {
                addProduct((Product) object);
            } else if (object instanceof AddProductCommand) {
                addProduct(((AddProductCommand) object).getProduct());
            } else {
                return;
            }
        }
    }

    protected void addProduct(Product product) {
        try {
            HttpClient client = HttpClient.newHttpClient();
             {
                String uri = LOCALHOST + "/add-product?name=" + product.getName() + "&price=" + product.getPrice();
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
