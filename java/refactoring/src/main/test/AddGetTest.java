import org.junit.jupiter.api.Test;
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

    AddProductCommand XBOX = new AddProductCommand("XBOX360", (long) 100500);
    AddProductCommand PS4 = new AddProductCommand("PS4", (long) 100499);


    @Test
    public void addGetSingleProduct() {
        List<AddProductCommand> commands = Collections.singletonList(XBOX);
        addProduct(XBOX);
        doTest(commands);
    }

    @Test
    public void addGetMultiplyProducts() {
        List<AddProductCommand> commands = Arrays.asList(XBOX, PS4);
        addProduct(XBOX);
        addProduct(PS4);
        doTest(commands);
    }

    private void doTest(List<AddProductCommand> commands) {
        StringBuilder expected = new StringBuilder();
        expected.append("<html><body>\n");
        for (AddProductCommand command : commands) {
            expected.append(command.getName()).append("\t").append(command.getPrice()).append("</br>\n");
        }
        expected.append("</body></html>\n");
        assertEquals(expected.toString(), getPathResponse("get-products"));
    }
}
