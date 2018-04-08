import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GeneralApplicationTest {
    @Test
    public void applicationPrintTest(){
        GeneralApplication app = new GeneralApplication();
        assertEquals("1. 添加学生 \n2. 生成成绩单 \n3. 退出 \n请输入你的选择（1~3）：", app.printHelpMessage());
    }
}
