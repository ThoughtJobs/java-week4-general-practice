import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GeneralApplicationTest {
    @Test
    public void applicationPrintTest(){
        GeneralApplication app = new GeneralApplication();
        assertEquals("hello", app.print());
    }
}
