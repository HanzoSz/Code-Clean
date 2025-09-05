/**
 * Represents a grade for a student in a course.
 */
public class Grade {
    private String studentId;
    private String courseId;
    private double score;

    /**
     * Constructs a Grade object.
     * @param studentId the ID of the student
     * @param courseId the ID of the course
     * @param score the score/grade
     */
    public Grade(String studentId, String courseId, double score) {
        this.studentId = studentId;
        this.courseId = courseId;
        this.score = score;
    }

    public String getStudentId() {
        return studentId;
    }

    public String getCourseId() {
        return courseId;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return String.format("Ma SV: %-10s | Ma MH: %-10s | Diem: %.2f", studentId, courseId, score);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Grade grade = (Grade) obj;
        return studentId.equals(grade.studentId) && courseId.equals(grade.courseId);
    }

    @Override
    public int hashCode() {
        int result = studentId != null ? studentId.hashCode() : 0;
        result = 31 * result + (courseId != null ? courseId.hashCode() : 0);
        return result;
    }
}