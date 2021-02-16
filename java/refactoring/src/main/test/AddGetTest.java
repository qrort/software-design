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
        List<AddProductCommand> commands = Collections.singletonList(new AddProductCommand(XBOX));
        addProduct(XBOX);
        doTest(commands);
    }

    @Test
    public void addGetMultiplyProducts() {
        List<AddProductCommand> commands = Arrays.asList(new AddProductCommand(XBOX), new AddProductCommand(PS4));
        addProduct(XBOX);
        addProduct(PS4);
        doTest(commands);
    }

    private void doTest(List<AddProductCommand> commands) {
        StringBuilder expected = new StringBuilder();
        expected.append("<html><body>\n");
        for (AddProductCommand command : commands) {
            expected.append(command.getProduct().getName()).append("\t").append(command.getProduct().getPrice()).append("</br>\n");
        }
        expected.append("</body></html>\n");
        assertEquals(expected.toString(), getPathResponse("get-products"));
    }
}
