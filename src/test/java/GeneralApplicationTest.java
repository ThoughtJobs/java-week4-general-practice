import org.junit.Test;
import java.util.Arrays;
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
    public void should_create_student_record_after_input_valid_record(){
        GeneralApplication app = new GeneralApplication(System.in);
        String input = "zhangsan, 001, Math: 80, Yuwen: 70, English: 90, Programming: 100";
        app.createStudentRecord(input);
        assertEquals(1, app.getStudents().size());
        Student student = app.getStudents().get(0);
        assertEquals("zhangsan", student.getName());
        assertEquals("001", student.getId());
        assertEquals(4, student.getCourses().size());
        assertEquals("Math", student.getCourses().get(0).getCourseName());
        assertEquals(80, student.getCourses().get(0).getScore());
        assertEquals("Yuwen", student.getCourses().get(1).getCourseName());
        assertEquals(70, student.getCourses().get(1).getScore());
        assertEquals("English", student.getCourses().get(2).getCourseName());
        assertEquals(90, student.getCourses().get(2).getScore());
        assertEquals("Programming", student.getCourses().get(3).getCourseName());
        assertEquals(100, student.getCourses().get(3).getScore());
    }

    @Test
    public void should_validate_step2_input_existing_student_id(){
        GeneralApplication app = new GeneralApplication(System.in);
        String input = "zhangsan, 001, Math: 80, Yuwen: 70, English: 90, Programming: 100";
        app.createStudentRecord(input);
        assertEquals(1, app.matchStep2Input("001").size());
    }

    @Test
    public void should_ignore_step2_input_notExist_student_id(){
        GeneralApplication app = new GeneralApplication(System.in);
        String input = "zhangsan, 001, Math: 80, Yuwen: 70, English: 90, Programming: 100";
        app.createStudentRecord(input);
        assertEquals(1, app.matchStep2Input("001, 002").size());
        assertEquals("001", app.matchStep2Input("001, 002").get(0));
    }

    @Test
    public void should_generate_student_score_sheet_for_given_student_id(){
        GeneralApplication app = new GeneralApplication(System.in);
        String input = "zhangsan, 001, Math: 80, Yuwen: 70, English: 90, Programming: 100";
        app.createStudentRecord(input);
        String scoreSheet = app.generateStudentScoreSheet(Arrays.asList("001"));
        assertEquals("成绩单\n" +
                "Name|Math|Yuwen|English|Programming|平均分|总分\n" +
                "========================\n" +
                "zhangsan|80|70|90|100|85|340\n" +
                "========================\n" +
                "全班总分平均数：340\n" +
                "全班总分中位数：340", scoreSheet);

    }
}