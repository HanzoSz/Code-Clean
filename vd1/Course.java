/**
 * Represents a course in the school system.
 */
public class Course {
    private String id;
    private String name;
    private int credits;

    /**
     * Constructs a Course object.
     * @param id the course ID
     * @param name the course name
     * @param credits the number of credits
     */
    public Course(String id, String name, int credits) {
        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCredits() {
        return credits;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCredits(int credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return String.format("ID: %-10s | Ten mon: %-25s | Tin chi: %d", id, name, credits);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Course course = (Course) obj;
        return id.equals(course.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}