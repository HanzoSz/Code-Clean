/**
 * Represents an enrollment of a student in a course.
 */
public class Enrollment {
    private String studentId;
    private String courseId;

    /**
     * Constructs an Enrollment object.
     * @param studentId the ID of the student
     * @param courseId the ID of the course
     */
    public Enrollment(String studentId, String courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    @Override
    public String toString() {
        return String.format("Ma SV: %-10s | Ma MH: %s", studentId, courseId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Enrollment that = (Enrollment) obj;
        return studentId.equals(that.studentId) && courseId.equals(that.courseId);
    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        return result;
    }
}