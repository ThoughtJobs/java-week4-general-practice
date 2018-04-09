package com.tw;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class LibraryTest {
    Library app = new Library();

    @Test
    public void applicationPrintTest(){
        assertEquals("1. 添加学生 \n2. 生成成绩单 \n3. 退出 \n请输入你的选择（1~3）：", app.printHelpMessage());
    }

    @Test
    public void should_validate_step1_input_message_format_as_true(){
        String input = "zhangsan, 001, Math: 80, Yuwen: 70, English: 90, Programming: 100";
        assertEquals(true, app.matchStep1Input(input));
    }

    @Test
    public void should_validate_step1_input_chinese_message_format_as_true(){
        String input = "张三, 001, 数学: 80, 语文: 70, 英语: 90, 编程: 100";
        assertEquals(true, app.matchStep1Input(input));
    }

    @Test
    public void should_validate_step1_input_message_format_as_false(){
        String input = "zhangsan";
        assertEquals(false, app.matchStep1Input(input));
    }

    @Test
    public void should_not_create_student_record_if_input_record_is_invalid(){
        String input = "zhangsan";
        Student s = app.createStudentRecord(input);
        assertEquals(null, s);
    }

    @Test
    public void should_create_student_record_if_input_record_is_valid(){
        String input = "zhangsan, 001, Math: 80, Yuwen: 70, English: 90, Programming: 100";
        Student student = app.createStudentRecord(input);
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
        String input = "zhangsan, 001, Math: 80, Yuwen: 70, English: 90, Programming: 100";
        app.createStudentRecord(input);
        assertEquals(1, app.matchStep2Input("001").size());
    }

    @Test
    public void should_ignore_step2_input_notExist_student_id(){
        String input = "zhangsan, 001, Math: 80, Yuwen: 70, English: 90, Programming: 100";
        app.createStudentRecord(input);
        assertEquals(1, app.matchStep2Input("001, 002").size());
        assertEquals("001", app.matchStep2Input("001, 002").get(0));
    }

    @Test
    public void should_match_multiple_student_ids(){
        String input = "zhangsan, 001, Math: 80, Yuwen: 70, English: 90, Programming: 100";
        app.createStudentRecord(input);
        input = "lisi, 002, Math: 85, Yuwen: 80, English: 70, Programming: 90";
        app.createStudentRecord(input);
        assertEquals(2, app.matchStep2Input("001, 002").size());
        assertEquals("001", app.matchStep2Input("001, 002").get(0));
        assertEquals("002", app.matchStep2Input("001, 002").get(1));
    }

    @Test
    public void should_generate_student_score_sheet_for_given_student_id(){
        String input = "zhangsan, 100, Math: 80, Yuwen: 70, English: 90, Programming: 100";
        app.createStudentRecord(input);
        String scoreSheet = app.generateStudentScoreSheet(Arrays.asList("100"));
        assertEquals("成绩单\n" +
                "Name|Math|Yuwen|English|Programming|平均分|总分\n" +
                "========================\n" +
                "zhangsan|80|70|90|100|85|340\n" +
                "========================\n" +
                "全班总分平均数：340\n" +
                "全班总分中位数：340", scoreSheet);
    }

    @Test
    public void should_generate_student_score_sheet_for_multiple_student_id(){
        String input = "zhangsan, 100, Math: 75, Yuwen: 95, English: 80, Programming: 80";
        app.createStudentRecord(input);
        input = "lisi, 101, Math: 85, Yuwen: 80, English: 70, Programming: 90";
        app.createStudentRecord(input);
        String scoreSheet = app.generateStudentScoreSheet(Arrays.asList("100", "101"));
        assertEquals("成绩单\n" +
                "Name|Math|Yuwen|English|Programming|平均分|总分\n" +
                "========================\n" +
                "zhangsan|75|95|80|80|82.5|330\n" +
                "lisi|85|80|70|90|81.25|325\n" +
                "========================\n" +
                "全班总分平均数：327.5\n" +
                "全班总分中位数：327.5", scoreSheet);
    }
}