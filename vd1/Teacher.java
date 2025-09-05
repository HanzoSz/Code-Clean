/**
 * Represents a teacher in the school system.
 */
public class Teacher {
    private String id;
    private String name;
    private String specialty;

    /**
     * Constructs a Teacher object.
     * @param id the teacher's ID
     * @param name the teacher's name
     * @param specialty the teacher's specialty
     */
    public Teacher(String id, String name, String specialty) {
        this.id = id;
        this.name = name;
        this.specialty = specialty;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSpecialty() {
        return specialty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    @Override
    public String toString() {
        return String.format("ID: %-10s | Tên: %-20s | Chuyên môn: %s", id, name, specialty);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Teacher teacher = (Teacher) obj;
        return id.equals(teacher.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}