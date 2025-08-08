// Student.java
public class Student {
    private String name;
    private String studentId;
    private double[] marks;
    private int subjectCount;

    public Student(String name, String studentId, int subjectCount) {
        this.name = name;
        this.studentId = studentId;
        this.subjectCount = subjectCount;
        this.marks = new double[subjectCount];
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    public double[] getMarks() {
        return marks;
    }

    public int getSubjectCount() {
        return subjectCount;
    }

    // Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public void setMark(int index, double mark) {
        if (index >= 0 && index < subjectCount) {
            marks[index] = mark;
        }
    }

    // Calculate average marks
    public double calculateAverage() {
        double sum = 0;
        for (double mark : marks) {
            sum += mark;
        }
        return sum / subjectCount;
    }

    // Calculate grade based on average
    public String calculateGrade() {
        double average = calculateAverage();

        if (average >= 90) {
            return "A+";
        } else if (average >= 85) {
            return "A";
        } else if (average >= 80) {
            return "B+";
        } else if (average >= 75) {
            return "B";
        } else if (average >= 70) {
            return "C+";
        } else if (average >= 65) {
            return "C";
        } else if (average >= 60) {
            return "D";
        } else {
            return "F";
        }
    }

    // Get grade description
    public String getGradeDescription() {
        String grade = calculateGrade();
        switch (grade) {
            case "A+": return "Outstanding";
            case "A": return "Excellent";
            case "B+": return "Very Good";
            case "B": return "Good";
            case "C+": return "Above Average";
            case "C": return "Average";
            case "D": return "Below Average";
            case "F": return "Fail";
            default: return "Unknown";
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", studentId='" + studentId + '\'' +
                ", average=" + String.format("%.2f", calculateAverage()) +
                ", grade='" + calculateGrade() + '\'' +
                '}';
    }
}