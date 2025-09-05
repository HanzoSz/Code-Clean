import java.util.ArrayList;

public class StudentService {
    private final ArrayList<Student> students;
    private final ArrayList<Enrollment> enrollments;
    private final ArrayList<Grade> grades;

    public StudentService(ArrayList<Student> students, ArrayList<Enrollment> enrollments, ArrayList<Grade> grades) {
        this.students = students;
        this.enrollments = enrollments;
        this.grades = grades;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public void removeStudent(Student student) {
        students.remove(student);
        enrollments.removeIf(e -> e.getStudentId().equals(student.getId()));
        grades.removeIf(g -> g.getStudentId().equals(student.getId()));
    }

    public Student findStudentById(String id) {
        return students.stream().filter(s -> s.getId().equals(id)).findFirst().orElse(null);
    }

    public ArrayList<Student> getAllStudents() {
        return students;
    }

}
