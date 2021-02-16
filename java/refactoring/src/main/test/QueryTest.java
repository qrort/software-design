import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.Product;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryTest extends ServiceBaseTest {

    @Test
    public void getEmptyMax() {
        doMaxTest(null);
    }

    @Test
    public void getEmptyMin() {
        doMinTest(null);
    }

    @Test
    public void getEmptySum() {
        doSumTest(0);
    }

    @Test
    public void getEmptyCount() {
        doCountTest(0);
    }

    @Test
    public void getMax() {
        addProducts(Arrays.asList(PS4, XBOX, KFCONSOLE));
        doMaxTest(XBOX);
    }

    @Test
    public void getMin() {
        addProducts(Arrays.asList(PS4, XBOX, KFCONSOLE));
        doMinTest(KFCONSOLE);
    }

    @Test
    public void getSum() {
        addProducts(Arrays.asList(PS4, XBOX, KFCONSOLE));
        doSumTest(PS4.getPrice() + XBOX.getPrice() + KFCONSOLE.getPrice());
    }

    @Test
    public void getCount() {
        addProducts(Arrays.asList(PS4, XBOX));
        doCountTest(2);
    }

    private void doMaxTest(Product maxProduct) {
        String body = "<h1>Product with max price: </h1>\n";
        if (maxProduct != null) {
            body = body + maxProduct.getName() + "\t" + maxProduct.getPrice() + "</br>\n";
        }
        doTest(body, "max");
    }

    private void doMinTest(Product minProduct) {
        String body = "<h1>Product with min price: </h1>\n";
        if (minProduct != null) {
            body = body + minProduct.getName() + "\t" + minProduct.getPrice() + "</br>\n";
        }
        doTest(body, "min");
    }

    private void doSumTest(long summaryPrice) {
        doTest("Summary price: \n" + summaryPrice + "\n", "sum");
    }

    private void doCountTest(int productsCnt) {
        doTest("Number of products: \n" + productsCnt + "\n", "count");
    }

    private void doTest(String bodyText, String query) {
        String expected = "<html><body>\n"
                + bodyText
                + "</body></html>\n";
        assertEquals(expected, getPathResponse("query?command=" + query));
    }
}
