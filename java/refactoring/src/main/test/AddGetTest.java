import org.junit.jupiter.api.Test;
import ru.akirakozov.sd.refactoring.Product;
import ru.akirakozov.sd.refactoring.command.AddProductCommand;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddGetTest extends ServiceBaseTest {
    @Test
    public void addGetEmpty() {
        doTest(Collections.emptyList());
    }

    @Test
    public void addGetSingleProduct() {
        List<Product> products = Collections.singletonList(XBOX);
        addProduct(XBOX);
        doTest(products);
    }

    @Test
    public void addGetMultipleProducts() {
        List<Product> products = Arrays.asList(XBOX, PS4);
        addProduct(XBOX);
        addProduct(PS4);
        doTest(products);
    }

    private void doTest(List<Product> products) {
        StringBuilder expected = new StringBuilder();
        expected.append("<html><body>\n");
        for (Product product : products) {
            expected.append(product.getName()).append("\t").append(product.getPrice()).append("</br>\n");
        }
        expected.append("</body></html>\n");
        assertEquals(expected.toString(), getPathResponse("get-products"));
    }
}
