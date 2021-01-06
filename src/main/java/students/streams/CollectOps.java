package students.streams;

import students.Student;

import java.util.List;
import java.util.stream.Collectors;

public class CollectOps {
  public static String letterGrade(Student s) {
    int grade = s.getGrade();
    if (grade > 90) return "A";
    if (grade > 80) return "B";
    if (grade > 70) return "C";
    return "D";
  }

  public static void main(String[] args) {
    List<Student> roster = List.of(
        Student.of("Fred", 76, "Math", "Physics"),
        Student.of("Albert", 95, "Math"),
        Student.of("Jim", 62, "Art"),
        Student.of("Sheila", 96, "Math", "Physics", "Quantum Mechanics", "Astrophysics")
    );
    roster.stream()
        .collect(Collectors.groupingBy(CollectOps::letterGrade))
        .entrySet()
        .forEach(System.out::println);
    System.out.println("----");
    roster.stream()
        .collect(Collectors.groupingBy(CollectOps::letterGrade,
            Collectors.counting()))
        .entrySet()
        .forEach(System.out::println);
  }
}
