# Refactor SchoolManager - Clean Code & So sánh với vd1.java

---

## 1. Đoạn code vi phạm clean code trong kiến trúc cũ (vd1.java)

### a. Vi phạm SRP (God class, quá nhiều trách nhiệm)
```java
// SchoolManager.java (trước refactor)
private static void addStudent() {
    System.out.print("Nhap ID sinh vien: ");
    String id = sc.nextLine();
    if (findStudentById(id) != null) {
        System.out.println("Loi: ID sinh vien da ton tai.");
        return;
    }
    System.out.print("Nhap ten: ");
    String name = sc.nextLine();
    System.out.print("Nhap tuoi: ");
    int age = getIntInput();
    System.out.print("Nhap GPA: ");
    double gpa = getDoubleInput();
    students.add(new Student(id, name, age, gpa)); // Vi phạm: thao tác trực tiếp dữ liệu
    System.out.println("Da them sinh vien thanh cong!");
}

private static void deleteTeacher() {
    System.out.print("Nhap ID giao vien can xoa: ");
    String id = sc.nextLine();
    Teacher teacher = findTeacherById(id);
    if (teacher == null) {
        System.out.println("Khong tim thay giao vien.");
    } else {
        teachers.remove(teacher); // Vi phạm: thao tác trực tiếp dữ liệu
        System.out.println("Da xoa giao vien.");
    }
}
```

### b. Vi phạm DRY (lặp lại logic CRUD cho từng loại đối tượng)
```java
// VD: Thêm sinh viên, thêm giáo viên, thêm môn học đều lặp lại cấu trúc tương tự
private static void addStudent() { /* ... */ students.add(...); }
private static void addTeacher() { /* ... */ teachers.add(...); }
private static void addCourse()  { /* ... */ courses.add(...); }
```

### c. Vi phạm tách biệt logic nghiệp vụ và dữ liệu
```java
// VD: Xóa sinh viên đồng thời phải xóa enrollments và grades liên quan ngay trong SchoolManager
private static void deleteStudent() {
    // ...
    students.remove(student);
    enrollments.removeIf(e -> e.getStudentId().equals(id));
    grades.removeIf(g -> g.getStudentId().equals(id));
    // ...
}
```

---

## 2. Vấn đề của kiến trúc cũ (vd1.java)
### a. Vi phạm nguyên tắc thiết kế
- **Single Responsibility Principle (SRP):**
  - Toàn bộ logic quản lý sinh viên, giáo viên, môn học, đăng ký, điểm đều nằm trong một class lớn (God class).
  - Một class có quá nhiều trách nhiệm, khó bảo trì, khó mở rộng.
- **Tight Coupling:**
  - Các thao tác CRUD truy cập trực tiếp vào các danh sách (ArrayList) trong class chính.
  - Không có sự tách biệt giữa logic nghiệp vụ và dữ liệu.
- **Khó kiểm thử (Testing):**
  - Không thể kiểm thử từng phần riêng biệt.
- **Khó mở rộng:**
  - Khi thêm chức năng mới, phải sửa trực tiếp vào class lớn, dễ gây lỗi dây chuyền.
- **Vi phạm DRY:**
  - Nhiều đoạn code lặp lại cho từng loại đối tượng (add, delete, update, find...)

### b. Ví dụ code vi phạm
```java
// VD: Thêm sinh viên (vi phạm SRP, thao tác trực tiếp trên danh sách)
private static void addStudent() {
    // ...
    students.add(new Student(id, name, age, gpa));
}

// VD: Xóa giáo viên (vi phạm SRP, thao tác trực tiếp trên danh sách)
private static void deleteTeacher() {
    // ...
    teachers.remove(teacher);
}
```

---

## 3. Kiến trúc mới - Clean Code với Service
### a. Đã tách các service riêng biệt
- `StudentService.java`: Quản lý sinh viên
- `TeacherService.java`: Quản lý giáo viên
- `CourseService.java`: Quản lý môn học
- `EnrollmentService.java`: Quản lý đăng ký học
- `GradeService.java`: Quản lý điểm

### b. SchoolManager chỉ còn nhiệm vụ điều phối
- Không thao tác trực tiếp với dữ liệu, chỉ gọi các service.
- Dễ bảo trì, dễ mở rộng, dễ kiểm thử.

### c. Đã loại bỏ các hàm helper và import dư thừa
- Mọi thao tác tìm kiếm, thêm, xóa, cập nhật đều thông qua service.

---

## 4. Ví dụ code mẫu từng phần (kiến trúc mới)

### a. StudentService
```java
Student newStudent = new Student("SV003", "Le Van D", 22, 8.2);
studentService.addStudent(newStudent);
Student s = studentService.findStudentById("SV003");
```

### b. TeacherService
```java
Teacher newTeacher = new Teacher("GV02", "Nguyen Thi E", "Toan");
teacherService.addTeacher(newTeacher);
for (Teacher t : teacherService.getAllTeachers()) {
    System.out.println(t);
}
```

### c. CourseService
```java
Course newCourse = new Course("MH03", "Cau truc du lieu", 4);
courseService.addCourse(newCourse);
Course c = courseService.findCourseById("MH03");
```

### d. EnrollmentService
```java
Enrollment e = new Enrollment("SV003", "MH03");
enrollmentService.addEnrollment(e);
boolean enrolled = enrollmentService.isEnrolled("SV003", "MH03");
```

### e. GradeService
```java
Grade g = new Grade("SV003", "MH03", 9.0);
gradeService.addOrUpdateGrade(g);
Grade grade = gradeService.findGrade("SV003", "MH03");
```

---

## 5. Lợi ích sau refactor
- **Tăng tính module hóa:** Mỗi service quản lý một loại dữ liệu, dễ thay đổi hoặc mở rộng độc lập.
- **Dễ kiểm thử:** Có thể test từng service riêng biệt.
- **Dễ bảo trì:** Sửa lỗi hoặc nâng cấp không ảnh hưởng toàn bộ hệ thống.
- **Chuẩn hóa clean code:** Đặt nền tảng cho các nguyên tắc thiết kế tốt hơn về sau.

---

## 6. Tổng kết
- Kiến trúc mới đã loại bỏ hoàn toàn các vi phạm clean code của vd1.java.
- Dự án đã sẵn sàng để mở rộng, bảo trì, kiểm thử và phát triển chuyên nghiệp.
