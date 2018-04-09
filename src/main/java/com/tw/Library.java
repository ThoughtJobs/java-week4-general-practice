package com.tw;

import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Library {
    private List<Student> students = new ArrayList<>();

    public static void main(String[] args){
        Library app = new Library();
        Scanner scanner = new Scanner(System.in);
        backToMainMenu(app);
        String s = scanner.nextLine();
        boolean pendingOnStep1 = false;
        boolean pendingOnStep2 = false;

        while(true){
            if(pendingOnStep1){
                if(app.matchStep1Input(s)){
                    Student student = app.createStudentRecord(s);
                    System.out.println(String.format("学生%s的成绩被添加", student.getName()));
                    pendingOnStep1 = false;
                    backToMainMenu(app);
                    s = scanner.nextLine();
                    continue;
                }else{
                    System.out.println("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：");
                    s = scanner.nextLine();
                    continue;
                }
            }
            if(pendingOnStep2){
                List<String> list = app.matchStep2Input(s);
                if(list.size() > 0){
                    System.out.println(app.generateStudentScoreSheet(list));
                    pendingOnStep2 = false;
                    backToMainMenu(app);
                    s = scanner.nextLine();
                    continue;
                }else{
                    System.out.println("请按正确的格式输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
                    s = scanner.nextLine();
                    continue;
                }
            }
            if(StringIsNumber(s) && Integer.parseInt(s) == 3) {
                System.out.println("thank you");
                break;
            }
            if(StringIsNumber(s) && Integer.parseInt(s) == 1) {
                System.out.println(app.printEnterStudentRecordMessage());
                pendingOnStep1 = true;
                s = scanner.nextLine();
                continue;
            }
            if(StringIsNumber(s) && Integer.parseInt(s) == 2) {
                System.out.println(app.printGenerateStudentScoreSheetMessage());
                pendingOnStep2 = true;
                s = scanner.nextLine();
                continue;
            }
            backToMainMenu(app);
            s = scanner.nextLine();
            continue;
        }
    }

    public static void backToMainMenu(Library app) {
        System.out.println(app.printHelpMessage());
    }

    public static boolean StringIsNumber(String s) {
        return s.chars().allMatch(Character::isDigit);
    }

    public String generateStudentScoreSheet(List<String> input) {
        List<Double> totalScores = new ArrayList<>();
        String output = "成绩单\n" +
                "Name|Math|Yuwen|English|Programming|平均分|总分\n" +
                "========================\n";
        for(String id: input){
            Student s = students.stream()
                    .filter(student -> student.getId().equals(id))
                    .collect(Collectors.toList()).get(0);
            output += s.PrintScoreSheet() + "\n";
            totalScores.add(s.GetTotalScore());
        }
        output += "========================\n";
        double classAverage, classTotalScore = 0.0;
        for(Double score: totalScores){
            classTotalScore += score;
        }
        classAverage = classTotalScore / totalScores.size();
        DecimalFormat df = new DecimalFormat("###.##");
        output += String.format("全班总分平均数：%s\n", df.format(classAverage));
        output += String.format("全班总分中位数：%s", df.format(getMedianScore(totalScores)));
        return output;
    }

    private Double getMedianScore(List<Double> totalScores) {
        totalScores.sort((o1, o2) -> o1 < o2 ? -1 : 1);
        int size = totalScores.size();
        return size % 2 != 0 ?
                totalScores.get(size / 2) :
                (totalScores.get(size / 2 -1) + totalScores.get(size / 2)) / 2;
    }

    public List<String> matchStep2Input(String input) {
        String[] ids = input.split(",\\s*");
        List<String> idList = Arrays.asList(ids);
        List<String> studentIds = students.stream().map(s -> s.getId()).collect(Collectors.toList());
        return studentIds.stream().filter(s -> idList.contains(s)).collect(Collectors.toList());
    }

    public boolean matchStep1Input(String message) {
        Matcher m = getStudentRecordInputMatcher(message);
        return m.find();
    }

    public Matcher getStudentRecordInputMatcher(String message) {
        String pattern = "(\\p{IsHan}+|[a-zA-Z]+),\\s*(\\d+),\\s*(((\\p{IsHan}+|[a-zA-Z]+):\\s*(\\d+),?\\s*)+)";
        Pattern p = Pattern.compile(pattern);
        return p.matcher(message);
    }

    public String printHelpMessage(){
        return "1. 添加学生 \n2. 生成成绩单 \n3. 退出 \n请输入你的选择（1~3）：";
    }

    public String printEnterStudentRecordMessage() {
        return "请输入学生信息（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：";
    }

    public String printGenerateStudentScoreSheetMessage(){
        return "请输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：";
    }

    public Student createStudentRecord(String message) {
        Matcher m = getStudentRecordInputMatcher(message);
        if(!m.find())
            return null;
        String name = m.group(1);
        String id = m.group(2);
        String scores = m.group(3);
        Student student = new Student();
        student.setName(name);
        student.setId(id);
        initializeScores(student, scores);
        students.add(student);
        return student;
    }

    private void initializeScores(Student student, String scores) {
        String[] split = scores.split(",\\s*");
        for(String item: split){
            String[] courseScore = item.split(":\\s*");
            String course = courseScore[0];
            String score = courseScore[1];
            student.AddCourse(course, Integer.parseInt(score));
        }
    }
}
