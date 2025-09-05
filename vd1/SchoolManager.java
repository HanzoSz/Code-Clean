// SchoolManager.java

import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Collectors;

public class SchoolManager {


    private static final ArrayList<Student> students = new ArrayList<>();
    private static final ArrayList<Teacher> teachers = new ArrayList<>();
    private static final ArrayList<Course> courses = new ArrayList<>();
    private static final ArrayList<Enrollment> enrollments = new ArrayList<>();
    private static final ArrayList<Grade> grades = new ArrayList<>();

    private static final StudentService studentService = new StudentService(students, enrollments, grades);
    private static final TeacherService teacherService = new TeacherService(teachers);
    private static final CourseService courseService = new CourseService(courses, enrollments, grades);
    private static final EnrollmentService enrollmentService = new EnrollmentService(enrollments);
    private static final GradeService gradeService = new GradeService(grades);

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        
        students.add(new Student("SV001", "Nguyen Van A", 20, 8.5));
        students.add(new Student("SV002", "Tran Thi B", 21, 7.9));
        teachers.add(new Teacher("GV01", "Le Thi C", "Khoa hoc May tinh"));
        courses.add(new Course("MH01", "Lap trinh Java", 3));
        courses.add(new Course("MH02", "Co so du lieu", 3));
        enrollments.add(new Enrollment("SV001", "MH01"));
        enrollments.add(new Enrollment("SV002", "MH01"));
        grades.add(new Grade("SV001", "MH01", 9.0));

        int choice = 0;
        while (choice != 99) {
            System.out.println("\n============= MENU CHINH =============");
            System.out.println("1. Quan ly Sinh vien");
            System.out.println("2. Quan ly Giao vien");
            System.out.println("3. Quan ly Mon hoc");
            System.out.println("4. Quan ly Dang ky hoc");
            System.out.println("5. Quan ly Diem");
            System.out.println("6. Bao cao tong hop");
            System.out.println("99. Thoat");
            System.out.print("Nhap lua chon: ");
            choice = getIntInput();

            switch (choice) {
                case 1: manageStudents(); break;
                case 2: manageTeachers(); break; 
                case 3: manageCourses(); break;
                case 4: manageEnrollments(); break;
                case 5: manageGrades(); break;
                case 6: generateComprehensiveReport(); break;
                case 99: System.out.println("Tam biet!"); break;
                default: System.out.println("Lua chon khong hop le.");
            }
        }
    }

    //======================= QUAN LY SINH VIEN =======================
    private static void manageStudents() {
        int choice = 0;
        while (choice != 9) {
            System.out.println("\n--- QUAN LY SINH VIEN ---");
            System.out.println("1. Them SV | 2. Xoa SV | 3. Cap nhat SV | 4. Hien thi tat ca SV");
            System.out.println("5. Tim SV theo ten | 6. Tim SV GPA > 8 | 7. Sap xep theo ten | 8. Sap xep theo GPA | 9. Quay lai");
            System.out.print("Lua chon: ");
            choice = getIntInput();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: deleteStudent(); break;
                case 3: updateStudent(); break;
                case 4: displayAll(studentService.getAllStudents()); break;
                case 5: findStudentByName(); break;
                case 6: findStudentsByGpa(); break;
                case 7: sortStudentsByName(); break;
                case 8: sortStudentsByGpa(); break;
            }
        }
    }

    private static void addStudent() {
        System.out.print("Nhap ID sinh vien: ");
        String id = sc.nextLine();
        if (studentService.findStudentById(id) != null) {
            System.out.println("Loi: ID sinh vien da ton tai.");
            return;
        }
        System.out.print("Nhap ten: ");
        String name = sc.nextLine();
        System.out.print("Nhap tuoi: ");
        int age = getIntInput();
        System.out.print("Nhap GPA: ");
        double gpa = getDoubleInput();

        studentService.addStudent(new Student(id, name, age, gpa));
        System.out.println("Da them sinh vien thanh cong!");
    }

    private static void deleteStudent() {
        System.out.print("Nhap ID sinh vien can xoa: ");
        String id = sc.nextLine();
        Student student = studentService.findStudentById(id);
        if (student == null) {
            System.out.println("Khong tim thay sinh vien.");
        } else {
            studentService.removeStudent(student);
            System.out.println("Da xoa sinh vien và các dữ liệu liên quan.");
        }
    }
    
    private static void updateStudent() {
        System.out.print("Nhap ID sinh vien can cap nhat: ");
        String id = sc.nextLine();
        Student student = studentService.findStudentById(id);
        if (student == null) {
            System.out.println("Khong tim thay sinh vien.");
            return;
        }
        System.out.print("Nhap ten moi (bo trong de giu nguyen): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) student.setName(name);

        System.out.print("Nhap tuoi moi (nhap -1 de giu nguyen): ");
        int age = getIntInput();
        if (age != -1) student.setAge(age);

        System.out.print("Nhap GPA moi (nhap -1 de giu nguyen): ");
        double gpa = getDoubleInput();
        if (gpa != -1) student.setGpa(gpa);
        
        System.out.println("Cap nhat thanh cong!");
    }
    
    private static void findStudentByName() {
        System.out.print("Nhap ten sinh vien can tim: ");
        String name = sc.nextLine();
        boolean found = false;
        for (Student s : studentService.getAllStudents()) {
            if (s.getName().equalsIgnoreCase(name)) {
                System.out.println(s);
                found = true;
            }
        }
        if (!found) System.out.println("Khong tim thay sinh vien nao co ten " + name);
    }
    
    private static void findStudentsByGpa() {
    System.out.println("Cac sinh vien co GPA > 8.0:");
    studentService.getAllStudents().stream()
        .filter(s -> s.getGpa() > 8.0)
        .forEach(System.out::println);
    }

    private static void sortStudentsByName() {
        studentService.getAllStudents().sort((s1, s2) -> s1.getName().compareToIgnoreCase(s2.getName()));
        System.out.println("Da sap xep danh sach sinh vien theo ten.");
        displayAll(studentService.getAllStudents());
    }
    
    private static void sortStudentsByGpa() {
        studentService.getAllStudents().sort((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()));
        System.out.println("Da sap xep danh sach sinh vien theo GPA (giam dan).");
        displayAll(studentService.getAllStudents());
    }
    
    //======================= QUAN LY GIAO VIEN =======================
    private static void manageTeachers() {
        int choice = 0;
        while (choice != 9) {
            System.out.println("\n--- QUAN LY GIAO VIEN ---");
            System.out.println("1. Them GV | 2. Xoa GV | 3. Cap nhat GV | 4. Hien thi tat ca GV");
            System.out.println("5. Tim GV theo ten | 6. Sap xep theo ten | 9. Quay lai");
            System.out.print("Lua chon: ");
            choice = getIntInput();
            switch (choice) {
                case 1:
                    addTeacher();
                    break;
                case 2:
                    deleteTeacher();
                    break;
                case 3:
                    updateTeacher();
                    break;
                case 4:
                    displayAll(teacherService.getAllTeachers());
                    break;
                case 5:
                    findTeacherByName();
                    break;
                case 6:
                    sortTeachersByName();
                    break;
                case 9:
                    System.out.println("Quay lai menu chinh.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
    }

    /**
     * Tìm giáo viên theo tên (gần đúng, không phân biệt hoa thường).
     */
    private static void findTeacherByName() {
        System.out.print("Nhap ten giao vien can tim: ");
        String name = sc.nextLine().toLowerCase();
        boolean found = false;
        for (Teacher t : teacherService.getAllTeachers()) {
            if (t.getName().toLowerCase().contains(name)) {
                System.out.println(t);
                found = true;
            }
        }
        if (!found) {
            System.out.println("Khong tim thay giao vien!");
        }
    }

    /**
     * Sắp xếp danh sách giáo viên theo tên (tăng dần).
     */
    private static void sortTeachersByName() {
        teacherService.getAllTeachers().sort((t1, t2) -> t1.getName().compareToIgnoreCase(t2.getName()));
        System.out.println("Da sap xep danh sach giao vien theo ten.");
        displayAll(teacherService.getAllTeachers());
    }

    private static void addTeacher() {
        System.out.print("Nhap ID giao vien: ");
        String id = sc.nextLine();
        if (teacherService.findTeacherById(id) != null) {
            System.out.println("Loi: ID giao vien da ton tai.");
            return;
        }
        System.out.print("Nhap ten GV: ");
        String name = sc.nextLine();
        System.out.print("Nhap chuyen mon: ");
        String specialty = sc.nextLine();
        teacherService.addTeacher(new Teacher(id, name, specialty));
        System.out.println("Da them giao vien thanh cong!");
    }

    private static void deleteTeacher() {
        System.out.print("Nhap ID giao vien can xoa: ");
        String id = sc.nextLine();
        Teacher teacher = teacherService.findTeacherById(id);
        if (teacher == null) {
            System.out.println("Khong tim thay giao vien.");
        } else {
            teacherService.removeTeacher(teacher);
            System.out.println("Da xoa giao vien.");
        }
    }

    private static void updateTeacher() {
        System.out.print("Nhap ID giao vien can cap nhat: ");
        String id = sc.nextLine();
        Teacher teacher = teacherService.findTeacherById(id);
        if (teacher == null) {
            System.out.println("Khong tim thay giao vien.");
            return;
        }
        System.out.print("Nhap ten moi (bo trong de giu nguyen): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) teacher.setName(name);

        System.out.print("Nhap chuyen mon moi (bo trong de giu nguyen): ");
        String specialty = sc.nextLine();
        if (!specialty.isEmpty()) teacher.setSpecialty(specialty);

        System.out.println("Cap nhat thanh cong!");
    }

    //======================= QUAN LY MON HOC =======================
    private static void manageCourses() {
        int choice = 0;
        while (choice != 9) {
            System.out.println("\n--- QUAN LY MON HOC ---");
            System.out.println("1. Them MH | 2. Xoa MH | 3. Cap nhat MH | 4. Hien thi tat ca MH | 9. Quay lai");
            System.out.print("Lua chon: ");
            choice = getIntInput();

            switch (choice) {
                case 1: addCourse(); break;
                case 2: deleteCourse(); break;
                case 3: updateCourse(); break;
                case 4: displayAll(courseService.getAllCourses()); break;
            }
        }
    }

    private static void addCourse() {
        System.out.print("Nhap ID mon hoc: ");
        String id = sc.nextLine();
        if (courseService.findCourseById(id) != null) {
            System.out.println("Loi: ID mon hoc da ton tai.");
            return;
        }
        System.out.print("Nhap ten mon hoc: ");
        String name = sc.nextLine();
        System.out.print("Nhap so tin chi: ");
        int credits = getIntInput();
        courseService.addCourse(new Course(id, name, credits));
        System.out.println("Da them mon hoc thanh cong!");
    }

    private static void deleteCourse() {
        System.out.print("Nhap ID mon hoc can xoa: ");
        String id = sc.nextLine();
        Course course = courseService.findCourseById(id);
        if (course == null) {
            System.out.println("Khong tim thay mon hoc.");
        } else {
            courseService.removeCourse(course);
            System.out.println("Da xoa mon hoc và các dữ liệu liên quan.");
        }
    }

    private static void updateCourse() {
        System.out.print("Nhap ID mon hoc can cap nhat: ");
        String id = sc.nextLine();
        Course course = courseService.findCourseById(id);
        if (course == null) {
            System.out.println("Khong tim thay mon hoc.");
            return;
        }
        System.out.print("Nhap ten moi (bo trong de giu nguyen): ");
        String name = sc.nextLine();
        if (!name.isEmpty()) course.setName(name);

        System.out.print("Nhap so tin chi moi (nhap -1 de giu nguyen): ");
        int credits = getIntInput();
        if (credits != -1) course.setCredits(credits);

        System.out.println("Cap nhat thanh cong!");
    }

    //======================= QUAN LY DANG KY HOC =======================
    private static void manageEnrollments() {
        int choice = 0;
        while (choice != 9) {
            System.out.println("\n--- QUAN LY DANG KY HOC ---");
            System.out.println("1. Dang ky mon hoc | 2. Huy dang ky | 3. Hien thi tat ca dang ky | 9. Quay lai");
            System.out.print("Lua chon: ");
            choice = getIntInput();

            switch (choice) {
                case 1: addEnrollment(); break;
                case 2: deleteEnrollment(); break;
                case 3: displayAll(enrollmentService.getAllEnrollments()); break;
            }
        }
    }

    private static void addEnrollment() {
        System.out.print("Nhap ID sinh vien: ");
        String studentId = sc.nextLine();
        if (studentService.findStudentById(studentId) == null) {
            System.out.println("Loi: Khong tim thay sinh vien.");
            return;
        }

        System.out.print("Nhap ID mon hoc: ");
        String courseId = sc.nextLine();
        if (courseService.findCourseById(courseId) == null) {
            System.out.println("Loi: Khong tim thay mon hoc.");
            return;
        }
        
        boolean alreadyEnrolled = enrollmentService.isEnrolled(studentId, courseId);
        if (alreadyEnrolled) {
            System.out.println("Loi: Sinh vien da dang ky mon hoc nay roi.");
            return;
        }

        enrollmentService.addEnrollment(new Enrollment(studentId, courseId));
        System.out.println("Dang ky mon hoc thanh cong!");
    }

    private static void deleteEnrollment() {
        System.out.print("Nhap ID sinh vien: ");
        String studentId = sc.nextLine();
        System.out.print("Nhap ID mon hoc can huy: ");
        String courseId = sc.nextLine();
        
        boolean wasEnrolled = enrollmentService.isEnrolled(studentId, courseId);
        enrollmentService.removeEnrollment(studentId, courseId);
        if (wasEnrolled) {
            gradeService.removeGrade(studentId, courseId);
            System.out.println("Huy dang ky thanh cong.");
        } else {
            System.out.println("Khong tim thay ban ghi dang ky phu hop.");
        }
    }
    
    //======================= QUAN LY DIEM =======================
    private static void manageGrades() {
        int choice = 0;
        while (choice != 9) {
            System.out.println("\n--- QUAN LY DIEM ---");
            System.out.println("1. Nhap/Cap nhat diem | 2. Hien thi bang diem | 9. Quay lai");
            System.out.print("Lua chon: ");
            choice = getIntInput();

            switch (choice) {
                case 1: addOrUpdateGrade(); break;
                case 2: displayAll(gradeService.getAllGrades()); break;
            }
        }
    }

    private static void addOrUpdateGrade() {
        System.out.print("Nhap ID sinh vien: ");
        String studentId = sc.nextLine();
        System.out.print("Nhap ID mon hoc: ");
        String courseId = sc.nextLine();
        
        boolean isEnrolled = enrollmentService.isEnrolled(studentId, courseId);

        if (!isEnrolled) {
            System.out.println("Loi: Sinh vien chua dang ky mon hoc nay. Khong the nhap diem.");
            return;
        }
        
        System.out.print("Nhap diem: ");
        double score = getDoubleInput();
        
        Grade grade = new Grade(studentId, courseId, score);
        Grade existingGrade = gradeService.findGrade(studentId, courseId);
        gradeService.addOrUpdateGrade(grade);
        if (existingGrade != null) {
            System.out.println("Da cap nhat diem thanh cong.");
        } else {
            System.out.println("Da them diem thanh cong.");
        }
    }

    //======================= BAO CAO TONG HOP =======================
    private static void generateComprehensiveReport() {
        System.out.println("\n================ BAO CAO TONG HOP ================");
        if (students.isEmpty()) {
            System.out.println("Chua co du lieu sinh vien.");
            return;
        }

        for (Student student : students) {
            System.out.println("\n--------------------------------------------------");
            System.out.println("Thong tin sinh vien: " + student.getName() + " (ID: " + student.getId() + ")");
            
            var studentEnrollments = enrollments.stream()
                .filter(e -> e.getStudentId().equals(student.getId()))
                .collect(Collectors.toList());

            if (studentEnrollments.isEmpty()) {
                System.out.println("-> Sinh vien chua dang ky mon hoc nao.");
            } else {
                System.out.println("-> Cac mon da dang ky:");
                for (Enrollment enrollment : studentEnrollments) {
                    Course course = courseService.findCourseById(enrollment.getCourseId());
                    Grade grade = gradeService.findGrade(student.getId(), enrollment.getCourseId());
                    
                    String courseName = (course != null) ? course.getName() : "Khong tim thay mon hoc";
                    String score = (grade != null) ? String.format("%.2f", grade.getScore()) : "Chua co diem";
                    
                    System.out.printf("  - Mon hoc: %-25s | Diem: %s\n", courseName, score);
                }
            }
        }
        System.out.println("================== KET THUC BAO CAO ==================");
    }

    //======================= CAC PHUONG THUC HO TRO =======================

    private static <T> void displayAll(ArrayList<T> list) {
        if (list.isEmpty()) {
            System.out.println("Danh sach trong.");
            return;
        }
        System.out.println("--------------------------------------------------");
        for (T item : list) {
            System.out.println(item);
        }
        System.out.println("--------------------------------------------------");
    }

    // Các phương thức hỗ trợ đã được thay thế bằng service, không còn dư thừa.

    private static int getIntInput() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Vui long nhap mot so nguyen hop le: ");
            }
        }
    }
    
    private static double getDoubleInput() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("Vui long nhap mot so thuc hop le: ");
            }
        }
    }
}