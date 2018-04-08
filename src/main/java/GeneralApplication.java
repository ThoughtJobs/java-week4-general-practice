import java.util.Scanner;

public class GeneralApplication {
    public static void main(String[] args){
        GeneralApplication app = new GeneralApplication();
        System.out.println(app.printHelpMessage());
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();

        while(true){
            if(s.chars().allMatch(Character::isDigit) && Integer.parseInt(s) == 3) {
                System.out.println("thank you");
                break;
            }
            System.out.println("your input is: " + s);
            System.out.println(app.printHelpMessage());
            s = scanner.nextLine();
        }

    }

    public String printHelpMessage(){
        return "1. 添加学生 \n2. 生成成绩单 \n3. 退出 \n请输入你的选择（1~3）：";
    }
}
