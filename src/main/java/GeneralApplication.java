import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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
        boolean pendingOnStep2 = false;

        while(true){
            if(pendingOnStep1){
                if(app.matchStep1Input(s)){
                    app.createStudentRecord(s);
                    System.out.println("学生xxx的成绩被添加");
                    pendingOnStep1 = false;
                    System.out.println(app.printHelpMessage());
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
                    System.out.println(app.printHelpMessage());
                    s = scanner.nextLine();
                    continue;
                }else{
                    System.out.println("请按正确的格式输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：");
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
            if(s.chars().allMatch(Character::isDigit) && Integer.parseInt(s) == 2) {
                System.out.println(app.printGenerateStudentScoreSheetMessage());
                pendingOnStep2 = true;
                s = scanner.nextLine();
                continue;
            }
        }
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
        double classAverage = 0.0, classTotalScore = 0.0;
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
        String[] ids = input.split(",");
        List<String> studentIds = students.stream().map(s -> s.getId()).collect(Collectors.toList());
        List<String> output = new ArrayList<>();
        for(String id: ids){
            boolean contains = studentIds.contains(id);
            if(contains){
                output.add(id);
            }
        }
        return output;
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

    public String printGenerateStudentScoreSheetMessage(){
        return "请输入要打印的学生的学号（格式： 学号, 学号,...），按回车提交：";
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
