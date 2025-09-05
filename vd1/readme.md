
# vd1 — So sánh Clean Code và Dirty Code (Toàn bộ)

Thư mục `vd1` chứa hai mẫu đại diện:

- `Vd1.java`: file mẫu "dirty code" — dữ liệu lưu dưới dạng chuỗi phân tách bằng dấu `|`, logic UI, lưu trữ và nghiệp vụ trộn lẫn, nhiều đoạn code bị sao chép.
- `SchoolManager.java` cùng các lớp dịch vụ (`StudentService`, `CourseService`, `GradeService`, `EnrollmentService`, `TeacherService`) — phiên bản tiếp cận chuẩn hơn: mô hình dữ liệu rõ ràng, tách trách nhiệm, dễ bảo trì.

Mục đích tài liệu này: giải thích khác biệt chính giữa hai phong cách, liệt kê rủi ro của code bẩn và cung cấp checklist di cư an toàn.

## 1. Khác biệt chính

- Mô hình dữ liệu
  - Dirty: dùng `ArrayList<String>` để lưu các bản ghi dưới dạng `id|name|...` — không có kiểu, dễ lỗi khi parse hoặc thay đổi định dạng.
  - Clean: dùng các lớp `Student`, `Course`, `Enrollment`, `Grade` với thuộc tính kiểu rõ ràng và các phương thức hỗ trợ.

- Trách nhiệm (SRP)
  - Dirty: một file lớn (God class) xử lý UI, thao tác dữ liệu và nghiệp vụ.
  - Clean: `SchoolManager` chỉ điều phối; các `*Service` chịu trách nhiệm CRUD, xóa rác liên quan, tìm kiếm.

- Tái sử dụng và tránh lặp (DRY)
  - Dirty: CRUD cho các thực thể được copy/paste nhiều lần.
  - Clean: service tái sử dụng logic, tránh duplication.

- Khả năng kiểm thử và bảo trì
  - Dirty: khó viết unit test, sửa đổi dễ gây lỗi lan truyền.
  - Clean: service có thể test riêng lẻ, dễ bảo trì.

## 2. Rủi ro khi giữ `Vd1.java` làm sản phẩm

- Lỗi parse và runtime: khi chuỗi có định dạng sai, chương trình sẽ ném NumberFormatException.
- Dữ liệu không nhất quán: không kiểm tra trùng ID, không có ràng buộc tính toàn vẹn.
- Khó mở rộng: thêm thực thể mới hoặc thay đổi trường sẽ phải sửa nhiều chỗ.

# Refactor SchoolManager - Clean Code & So sánh với vd1.java


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
# vd1 — Clean vs Dirty (tóm tắt)

Phiên bản trong thư mục `vd1` chứa hai mẫu:

- `Vd1.java` — file mẫu "dirty code" (pipe-delimited strings, nhiều lặp, chỉ để phân tích).
- `SchoolManager.java` + service classes — phiên bản "cleaner" (models + services, tách trách nhiệm).

Mục đích của tài liệu này: nhanh chóng chỉ ra khác biệt, rủi ro của code bẩn và các bước an toàn để chuyển sang kiến trúc sạch.

## So sánh ngắn

- Data model
  - Dirty: `ArrayList<String>` với dòng `id|name|...` — không an toàn, dễ lỗi parse.
  - Clean: `Student`, `Course`, `Enrollment`, `Grade` — kiểu rõ ràng, encapsulation.

- Responsibility
  - Dirty: một file/main chứa UI, lưu trữ, business logic — God class.
  - Clean: `SchoolManager` điều phối, các `*Service` chịu trách nhiệm CRUD và cleanup.

- Reuse & Duplication
  - Dirty: logic CRUD copy/paste cho SV/GV/MH.
  - Clean: pattern CRUD nằm trong service, tái sử dụng dễ dàng.

- Testability & Maintenance
  - Dirty: khó unit-test, dễ break khi thay đổi.
  - Clean: services dễ test, thay đổi cục bộ ít ảnh hưởng.

## Rủi ro chính của `Vd1.java`

- Parsing errors (NumberFormatException) nếu input không đúng định dạng.
- Duplicate IDs or inconsistent state (no central validation).
- Hard to extend: adding a new entity requires copying large blocks of code.

## Migration checklist (an toàn, từng bước)

1. Treat `Vd1.java` as a read-only sample (file bẩn). Do not run or rely on it for production.
2. Keep/extend the service-based code in `SchoolManager` and services — this is the canonical, maintainable path.
3. If you need to import data from old format, write a small converter that reads pipe-delimited strings and creates domain objects (non-destructive import).
4. Add validation when creating domain objects (ID uniqueness, numeric ranges).
5. Add small unit tests for services (one entity at a time) — run locally when ready.

## Low-risk improvements you can apply now

- Document public service methods (`Javadoc`).
- Add input validation in `SchoolManager` (already has `getIntInput()` / `getDoubleInput()` helpers).
- Consider returning `List<T>` instead of concrete `ArrayList<T>` in service APIs (refactor callers together).

## Next steps I can implement (chọn 1 nếu muốn tôi làm)

1) Giữ nguyên `Vd1.java`, tạo converter tool to import old data into domain objects. (safe, non-destructive)
2) Add a short README in project root describing how to run `SchoolManager` (no build steps required here).  
3) Make small, safe refactors in services (docstrings, defensive copies).  

Tôi đã không sửa `Vd1.java`. Nếu bạn muốn tôi tiếp tục (ví dụ: tạo công cụ chuyển đổi hoặc cập nhật docs ở root), chọn một mục từ "Next steps" hoặc mô tả hành động cụ thể.
Student newStudent = new Student("SV003", "Le Van D", 22, 8.2);
