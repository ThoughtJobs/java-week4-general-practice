public class GeneralApplication {
    public static void main(String[] args){
        GeneralApplication app = new GeneralApplication();
        System.out.println(app.printHelpMessage());
    }

    public String printHelpMessage(){
        return "1. 添加学生 \n2. 生成成绩单 \n3. 退出 \n请输入你的选择（1~3）：";
    }
}
