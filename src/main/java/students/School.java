package students;

import java.util.ArrayList;
import java.util.List;

interface Criterion {
  boolean test(Student s);
}

class SmartCriterion implements Criterion {
  private int threshold;
  public SmartCriterion(int threshold) {
    this.threshold = threshold;
  }

  @Override
  public boolean test(Student s) {
    return s.getGrade() > threshold;
  }
}

class EnthusiasticCriterion implements Criterion {
  @Override
  public boolean test(Student s) {
    return s.getCourses().size() > 2;
  }
}

public class School {
  // pass an argument main for its behavior "Command" pattern.
  public static List<Student> getStudentsByCriterion(List<Student> ls, Criterion crit) {
    List<Student> res = new ArrayList<>();
    for (Student s : ls) {
      if (crit.test(s)) {
        res.add(s);
      }
    }
    return res;
  }

//  public static List<Student> getEnthusiastictudents(List<Student> ls, int threshold) {
//    List<Student> res = new ArrayList<>();
//    for (Student s : ls) {
//      if (s.getCourses().size() > threshold) {
//        res.add(s);
//      }
//    }
//    return res;
//  }

  public static void showStudents(List<Student> ls) {
    for (Student s : ls) {
      System.out.println("> " + s);
    }
    System.out.println("-------------------");
  }

  public static void main(String[] args) {
    List<Student> roster = List.of(
        Student.of("Fred", 76, "Math", "Physics"),
        Student.of("Jim", 62, "Art"),
        Student.of("Sheila", 96, "Math", "Physics", "Quantum Mechanics", "Astrophysics")
    );
//    showStudents(getSmartStudents(roster, 80));
    showStudents(getStudentsByCriterion(roster, new SmartCriterion(70)));
    showStudents(getStudentsByCriterion(roster, new EnthusiasticCriterion()));
  }
}
