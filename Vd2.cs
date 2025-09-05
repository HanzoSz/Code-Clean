using System;
using System.Collections.Generic;
using System.Linq;

// Định nghĩa class Student cc
public class Student
{
    public string Id { get; set; }
    public string Name { get; set; }
    public int Age { get; set; }
    public double GPA { get; set; }

    public override string ToString()
    {
        return $"ID:{Id} Name:{Name} Age:{Age} GPA:{GPA}";
    }
}

// Service quản lý sinh viên
public class StudentService
{
    private List<Student> students = new List<Student>();

    public void AddStudent(Student s) => students.Add(s);

    public void RemoveStudent(string id) =>
        students.RemoveAll(s => s.Id == id);

    public void UpdateStudent(string id, string newName, int newAge, double newGpa)
    {
        var student = students.FirstOrDefault(s => s.Id == id);
        if (student != null)
        {
            student.Name = newName;
            student.Age = newAge;
            student.GPA = newGpa;
        }
    }

    public void ShowAll() =>
        students.ForEach(s => Console.WriteLine(s));

    public void FindByName(string name) =>
        students.Where(s => s.Name == name)
                .ToList()
                .ForEach(s => Console.WriteLine("Tìm thấy: " + s));

    public void ShowGoodStudents() =>
        students.Where(s => s.GPA > 8.0)
                .ToList()
                .ForEach(s => Console.WriteLine("SV giỏi: " + s));

    public void SortByName() =>
        students = students.OrderBy(s => s.Name).ToList();

    public void SortByGpaDesc() =>
        students = students.OrderByDescending(s => s.GPA).ToList();
}

// Chương trình chính
public class CleanSchoolProgram
{
    public static void Main(string[] args)
    {
        var studentService = new StudentService();

        studentService.AddStudent(new Student { Id = "S01", Name = "An", Age = 20, GPA = 7.5 });
        studentService.AddStudent(new Student { Id = "S02", Name = "Bình", Age = 21, GPA = 9.0 });

        Console.WriteLine("--- Danh sách SV ---");
        studentService.ShowAll();

        Console.WriteLine("--- SV giỏi ---");
        studentService.ShowGoodStudents();

        Console.WriteLine("--- Sắp xếp theo GPA ---");
        studentService.SortByGpaDesc();
        studentService.ShowAll();
    }
}
