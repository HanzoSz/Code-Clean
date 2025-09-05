import java.util.ArrayList;

public class CourseService {
    private final ArrayList<Course> courses;
    private final ArrayList<Enrollment> enrollments;
    private final ArrayList<Grade> grades;

    public CourseService(ArrayList<Course> courses, ArrayList<Enrollment> enrollments, ArrayList<Grade> grades) {
        this.courses = courses;
        this.enrollments = enrollments;
        this.grades = grades;
    }

    public void addCourse(Course course) {
        courses.add(course);
    }

    public void removeCourse(Course course) {
        courses.remove(course);
        enrollments.removeIf(e -> e.getCourseId().equals(course.getId()));
        grades.removeIf(g -> g.getCourseId().equals(course.getId()));
    }

    public Course findCourseById(String id) {
        return courses.stream().filter(c -> c.getId().equals(id)).findFirst().orElse(null);
    }

    public ArrayList<Course> getAllCourses() {
        return courses;
    }

    // ... (other course-related methods can be added here)
}
