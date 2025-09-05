import java.util.ArrayList;

public class GradeService {
    private final ArrayList<Grade> grades;

    public GradeService(ArrayList<Grade> grades) {
        this.grades = grades;
    }

    public void addOrUpdateGrade(Grade grade) {
        Grade existing = findGrade(grade.getStudentId(), grade.getCourseId());
        if (existing != null) {
            existing.setScore(grade.getScore());
        } else {
            grades.add(grade);
        }
    }

    public Grade findGrade(String studentId, String courseId) {
        return grades.stream()
                .filter(g -> g.getStudentId().equals(studentId) && g.getCourseId().equals(courseId))
                .findFirst().orElse(null);
    }

    public void removeGrade(String studentId, String courseId) {
        grades.removeIf(g -> g.getStudentId().equals(studentId) && g.getCourseId().equals(courseId));
    }

    public ArrayList<Grade> getAllGrades() {
        return grades;
    }

}
