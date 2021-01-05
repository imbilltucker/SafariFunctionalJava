package students;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class School2 {
  // Higher order function :)
  public static <E> Predicate<E> and(Predicate<E> first, Predicate<E> second) {
    return e -> first.test(e) && second.test(e);
  }

  public static <E> Predicate<E> and(Predicate<E> ... many) {
    return e -> {
      for (Predicate<E> p : many) {
        if (!p.test(e)) return false;
      }
      return true;
    };
  }

  public static <E> List<E> filter(List<E> ls, Predicate<E> crit) {
    List<E> res = new ArrayList<>();
    for (E s : ls) {
      if (crit.test(s)) {
        res.add(s);
      }
    }
    return res;
  }

  public static <E> void show(List<E> ls) {
    for (E s : ls) {
      System.out.println("> " + s);
    }
    System.out.println("-------------------");
  }

  public static void main(String[] args) {
    List<Student> roster = List.of(
        Student.of("Fred", 76, "Math", "Physics"),
        Student.of("Albert", 95, "Math"),
        Student.of("Jim", 62, "Art"),
        Student.of("Sheila", 96, "Math", "Physics", "Quantum Mechanics", "Astrophysics")
    );

    show(filter(roster, s -> s.getCourses().size() < 3));

    List<String> names = List.of("Albert", "Jane", "William", "Alice");
    show(filter(names, s -> s.length() > 5));

//    show(filter(roster, s -> s.isSmart()));
//    show(filter(roster, (Student s) -> s.isSmart()));
    show(filter(roster, Student::isSmart)); // "Method references"

    show(filter(roster, Student.getSmartPredicate(80)));

//    show(filter(roster, Student.getSmartPredicate(70)));
//    show(filter(roster, Student.getEnthusiastic(1)));

//    show(filter(filter(roster, Student.getEnthusiastic(1)), Student.getSmartPredicate(70)));

    // NEITHER WORK!
//    show(filter(roster, Student.getEnthusiastic(1), Student.getSmartPredicate(70)));
//    show(filter(roster, Student.getEnthusiastic(1) && Student.getSmartPredicate(70)));

//    show(filter(roster, and(Student.getEnthusiastic(1), Student.getSmartPredicate(70))));
    show(filter(roster, Student.getEnthusiastic(1).and(Student.getSmartPredicate(70))));
    show(filter(roster, Student.getSmartPredicate(70).and(Student.getEnthusiastic(1).negate())));
  }
}
