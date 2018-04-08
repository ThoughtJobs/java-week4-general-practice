import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class GeneralApplicationTest {
    @Test
    public void applicationPrintTest(){
        GeneralApplication app = new GeneralApplication(System.in);
        assertEquals("1. 添加学生 \n2. 生成成绩单 \n3. 退出 \n请输入你的选择（1~3）：", app.printHelpMessage());
    }

    @Test
    public void should_validate_step1_input_message_format_as_true(){
        String input = "zhangsan, 001, Math: 80, Yuwen: 70, English: 90, Programming: 100";
        GeneralApplication app = new GeneralApplication(System.in);
        assertEquals(true, app.matchStep1Input(input));
    }

    @Test
    public void should_validate_step1_input_message_format_as_false(){
        String input = "zhangsan";
        GeneralApplication app = new GeneralApplication(System.in);
        assertEquals(false, app.matchStep1Input(input));
    }

    @Test
    public void should_create_student_record_after_input(){
        GeneralApplication app = new GeneralApplication(System.in);
        String input = "zhangsan, 001, Math: 80, Yuwen: 70, English: 90, Programming: 100";
        app.createStudentRecord(input);
        assertEquals(1, app.getStudents().size());
        Student student = app.getStudents().get(0);
        assertEquals("zhangsan", student.getName());
        assertEquals("001", student.getId());
        assertEquals(4, student.getCourses().size());
    }
}