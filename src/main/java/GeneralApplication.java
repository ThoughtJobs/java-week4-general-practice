import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GeneralApplication {
    private InputStream inputStream;
    private List<Student> students = new ArrayList<>();

    public GeneralApplication(InputStream stream){
        this.inputStream = stream;
    }
    public static void main(String[] args){
        GeneralApplication app = new GeneralApplication(System.in);
        System.out.println(app.printHelpMessage());
        Scanner scanner = new Scanner(app.getInputStream());
        String s = scanner.nextLine();
        boolean pendingOnStep1 = false;

        while(true){
            if(pendingOnStep1){
                if(app.matchStep1Input(s)){
                    System.out.println("学生xxx的成绩被添加");
                    pendingOnStep1 = false;
                }else{
                    System.out.println("请按正确的格式输入（格式：姓名, 学号, 学科: 成绩, ...）：");
                    s = scanner.nextLine();
                    continue;
                }
            }
            if(s.chars().allMatch(Character::isDigit) && Integer.parseInt(s) == 3) {
                System.out.println("thank you");
                break;
            }
            if(s.chars().allMatch(Character::isDigit) && Integer.parseInt(s) == 1) {
                System.out.println(app.printEnterStudentRecordMessage());
                pendingOnStep1 = true;
                s = scanner.nextLine();
                continue;
            }

            System.out.println("your input is: " + s);
            System.out.println(app.printHelpMessage());
            s = scanner.nextLine();
        }

    }

    public boolean matchStep1Input(String message) {
        String pattern = "([a-zA-Z]+), (\\d+), ([a-zA-Z]+): (\\d+), ([a-zA-Z]+): (\\d+), ([a-zA-Z]+): (\\d+), ([a-zA-Z]+): (\\d+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(message);
        boolean b = m.find();
        return b;
    }

    public String printHelpMessage(){
        return "1. 添加学生 \n2. 生成成绩单 \n3. 退出 \n请输入你的选择（1~3）：";
    }

    public InputStream getInputStream() {
        return inputStream;
    }

    public String printEnterStudentRecordMessage() {
        return "请输入学生信息（格式：姓名, 学号, 学科: 成绩, ...），按回车提交：";
    }

    public void createStudentRecord(String message) {
        String pattern = "([a-zA-Z]+), (\\d+), ([a-zA-Z]+): (\\d+), ([a-zA-Z]+): (\\d+), ([a-zA-Z]+): (\\d+), ([a-zA-Z]+): (\\d+)";
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(message);
        boolean b = m.find();
        if(b){
            String name = m.group(1);
            String id = m.group(2);
            Student student = new Student();
            student.setName(name);
            student.setId(id);
            String course1 = m.group(3);
            int course1Score = Integer.parseInt(m.group(4));
            student.AddCourse(course1, course1Score);

            String course2 = m.group(5);
            int course2Score = Integer.parseInt(m.group(6));
            student.AddCourse(course2, course2Score);

            String course3 = m.group(7);
            int course3Score = Integer.parseInt(m.group(8));
            student.AddCourse(course3, course3Score);

            String course4 = m.group(9);
            int course4Score = Integer.parseInt(m.group(10));
            student.AddCourse(course4, course4Score);
            students.add(student);
        }
    }

    public List<Student> getStudents() {
        return students;
    }
}
