import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class Student {
    private String name;
    private String Id;
    private List<Course> courses = new ArrayList<>();

    public String getName() {
        return name;
    }


    public String getId() {
        return Id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        Id = id;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void AddCourse(String course, int score){
        courses.add(new Course(course, score));
    }

    public String PrintScoreSheet(){
        double totalScore = 0;
        String print = name + "|";
        for(Course c: courses){
            print += c.getScore() + "|";
            totalScore += c.getScore();
        }
        DecimalFormat df = new DecimalFormat("###.#");
        double average = totalScore / courses.size();
        print += df.format(average) + "|" + df.format(totalScore);
        return print;
    }

    public double GetTotalScore(){
        double totalScore = 0;
        for(Course c: courses){
            totalScore += c.getScore();
        }
        return totalScore;
    }
}
