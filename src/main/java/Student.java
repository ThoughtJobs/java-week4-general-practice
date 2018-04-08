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
}
