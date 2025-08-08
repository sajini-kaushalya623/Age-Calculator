// GradeCalculator.java
import java.util.ArrayList;
import java.util.List;

public class GradeCalculator {
    private List<Student> students;

    public GradeCalculator() {
        this.students = new ArrayList<>();
    }

    // Add a student to the list
    public void addStudent(Student student) {
        students.add(student);
    }

    // Remove a student from the list
    public void removeStudent(Student student) {
        students.remove(student);
    }

    // Get all students
    public List<Student> getStudents() {
        return new ArrayList<>(students);
    }

    // Find student by ID
    public Student findStudentById(String studentId) {
        for (Student student : students) {
            if (student.getStudentId().equals(studentId)) {
                return student;
            }
        }
        return null;
    }

    // Validate marks (should be between 0 and 100)
    public boolean isValidMark(double mark) {
        return mark >= 0 && mark <= 100;
    }

    // Validate student name (should not be empty)
    public boolean isValidName(String name) {
        return name != null && !name.trim().isEmpty();
    }

    // Validate student ID (should not be empty)
    public boolean isValidStudentId(String studentId) {
        return studentId != null && !studentId.trim().isEmpty();
    }

    // Get class average
    public double getClassAverage() {
        if (students.isEmpty()) {
            return 0.0;
        }

        double sum = 0;
        for (Student student : students) {
            sum += student.calculateAverage();
        }
        return sum / students.size();
    }

    // Get highest performing student
    public Student getHighestPerformingStudent() {
        if (students.isEmpty()) {
            return null;
        }

        Student highest = students.get(0);
        for (Student student : students) {
            if (student.calculateAverage() > highest.calculateAverage()) {
                highest = student;
            }
        }
        return highest;
    }

    // Get lowest performing student
    public Student getLowestPerformingStudent() {
        if (students.isEmpty()) {
            return null;
        }

        Student lowest = students.get(0);
        for (Student student : students) {
            if (student.calculateAverage() < lowest.calculateAverage()) {
                lowest = student;
            }
        }
        return lowest;
    }

    // Get grade distribution
    public String getGradeDistribution() {
        if (students.isEmpty()) {
            return "No students data available";
        }

        int[] gradeCount = new int[8]; // A+, A, B+, B, C+, C, D, F
        String[] grades = {"A+", "A", "B+", "B", "C+", "C", "D", "F"};

        for (Student student : students) {
            String grade = student.calculateGrade();
            for (int i = 0; i < grades.length; i++) {
                if (grade.equals(grades[i])) {
                    gradeCount[i]++;
                    break;
                }
            }
        }

        StringBuilder distribution = new StringBuilder("Grade Distribution:\n");
        for (int i = 0; i < grades.length; i++) {
            if (gradeCount[i] > 0) {
                distribution.append(grades[i]).append(": ").append(gradeCount[i]).append(" students\n");
            }
        }

        return distribution.toString();
    }
}