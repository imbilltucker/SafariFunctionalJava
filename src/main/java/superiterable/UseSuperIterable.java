package superiterable;

import students.Student;

import java.util.List;

public class UseSuperIterable {
  public static void main(String[] args) {
    SuperIterable<String> sis = new SuperIterable<>(List.of("Fred", "Jim", "Sheila"));
    for (String s : sis) {
      System.out.println("> " + s);
    }

    System.out.println("-------");
    sis
        .filter(s -> s.length() > 3)
//        .map(s -> s.toUpperCase())
        .map(String::toUpperCase)
//        .doToAll(s -> System.out.println(s));
        .forEach(System.out::println);

    System.out.println("-------");
    List<Student> roster = List.of(
        Student.of("Fred", 76, "Math", "Physics"),
        Student.of("Albert", 95, "Math"),
        Student.of("Jim", 62, "Art"),
        Student.of("Sheila", 96, "Math", "Physics", "Quantum Mechanics", "Astrophysics")
    );
    SuperIterable<Student> rosterSis = new SuperIterable<>(roster);

    rosterSis
        .filter(s -> s.getGrade() > 70)
        .map(s -> s.getName() + " is working hard with a grade of " + s.getGrade())
        .forEach(System.out::println);
    System.out.println("-------------------------");
    rosterSis
        .peek(s -> System.out.println("peek before filter: " + s))
        .filter(s -> s.getGrade() > 70)
        .peek(s -> System.out.println("peek after filter: " + s))
        .flatMap(s -> new SuperIterable<>(s.getCourses()))
        .map(c -> "Someone is studying " + c)
        .forEach(System.out::println)
    ;

    System.out.println("-------------------------");
    roster.stream()
        .peek(s -> System.out.println("peek before filter: " + s))
        .filter(s -> s.getGrade() > 70)
        .peek(s -> System.out.println("peek after filter: " + s))
        .flatMap(s -> s.getCourses().stream())
        .map(c -> "Someone is studying " + c)
        .forEach(System.out::println)
    ;

  }
}
