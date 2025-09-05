import java.util.ArrayList;

public class EnrollmentService {
    private final ArrayList<Enrollment> enrollments;

    public EnrollmentService(ArrayList<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public void addEnrollment(Enrollment enrollment) {
        enrollments.add(enrollment);
    }

    public void removeEnrollment(String studentId, String courseId) {
        enrollments.removeIf(e -> e.getStudentId().equals(studentId) && e.getCourseId().equals(courseId));
    }

    public boolean isEnrolled(String studentId, String courseId) {
        return enrollments.stream().anyMatch(e -> e.getStudentId().equals(studentId) && e.getCourseId().equals(courseId));
    }

    public ArrayList<Enrollment> getAllEnrollments() {
        return enrollments;
    }

}
