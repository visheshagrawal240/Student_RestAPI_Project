package com.kiet.student;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "*")
public class StudentController {

    private final List<Student> students = new ArrayList<>();

    // Initial student data
    public StudentController() {

        students.add(new Student(
                101, "Rahul Sharma", "rahul@kiet.edu",
                "CSE", 2, 8.65
        ));

        students.add(new Student(
                102, "Priya Verma", "priya@kiet.edu",
                "CSE", 2, 9.20
        ));

        students.add(new Student(
                103, "Aman Singh", "aman@kiet.edu",
                "ECE", 3, 8.10
        ));

        students.add(new Student(
                104, "Neha Gupta", "neha@kiet.edu",
                "CSE", 3, 9.10
        ));

        students.add(new Student(
                105, "Rohit Kumar", "rohit@kiet.edu",
                "IT", 2, 7.80
        ));
    }

    // 1. Get all students
    @GetMapping
    public List<Student> getStudents() {
        return students;
    }

    // 2. Get student by ID
    @GetMapping("/{studentId}")
    public Object getStudentById(@PathVariable int studentId) {

        for (Student student : students) {
            if (student.getStudentId() == studentId) {
                return student;
            }
        }

        return Map.of("message", "Student not found");
    }

    // 3. Add a new student
    @PostMapping
    public Student addStudent(@RequestBody Student student) {

        students.add(student);

        return student;
    }

    // 4. Update student
    @PutMapping("/{studentId}")
    public Object updateStudent(
            @PathVariable int studentId,
            @RequestBody Student updatedStudent) {

        for (Student student : students) {

            if (student.getStudentId() == studentId) {

                student.setName(updatedStudent.getName());
                student.setEmail(updatedStudent.getEmail());
                student.setBranch(updatedStudent.getBranch());
                student.setYear(updatedStudent.getYear());
                student.setCgpa(updatedStudent.getCgpa());

                return student;
            }
        }

        return Map.of("message", "Student not found");
    }

    // 5. Delete student
    @DeleteMapping("/{studentId}")
    public Map<String, String> deleteStudent(
            @PathVariable int studentId) {

        boolean removed = students.removeIf(
                student -> student.getStudentId() == studentId
        );

        if (removed) {
            return Map.of(
                    "message", "Student deleted successfully"
            );
        }

        return Map.of(
                "message", "Student not found"
        );
    }

    // 6. Search students by branch and year
    @GetMapping("/search")
    public List<Student> searchStudents(
            @RequestParam String branch,
            @RequestParam int year) {

        List<Student> result = new ArrayList<>();

        for (Student student : students) {

            if (student.getBranch().equalsIgnoreCase(branch)
                    && student.getYear() == year) {

                result.add(student);
            }
        }

        return result;
    }

    // 7. Find students by minimum CGPA
    @GetMapping("/topper")
    public List<Student> getToppers(
            @RequestParam double cgpa) {

        List<Student> result = new ArrayList<>();

        for (Student student : students) {

            if (student.getCgpa() >= cgpa) {
                result.add(student);
            }
        }

        return result;
    }
}