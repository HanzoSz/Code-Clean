import java.util.ArrayList;

public class TeacherService {
    private final ArrayList<Teacher> teachers;

    public TeacherService(ArrayList<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void addTeacher(Teacher teacher) {
        teachers.add(teacher);
    }

    public void removeTeacher(Teacher teacher) {
        teachers.remove(teacher);
    }

    public Teacher findTeacherById(String id) {
        return teachers.stream().filter(t -> t.getId().equals(id)).findFirst().orElse(null);
    }

    public ArrayList<Teacher> getAllTeachers() {
        return teachers;
    }

}
