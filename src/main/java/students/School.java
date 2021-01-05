package students;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@FunctionalInterface
interface Silly {
  boolean banana(Student s);
}

@FunctionalInterface
interface Criterion {
  boolean test(Student s)/* throws IOException*/; // EXACLTLY ONE ABSTRACT METHOD!!!!
//  void bad();
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

// look up "anonymous inner class" too... for extras
class EnthusiasticCriterion implements Criterion {
  @Override
  public boolean test(Student s) {
    return s.getCourses().size() > 2;
  }
}

public class School {
  // pass an argument main for its behavior "Command" pattern.
  // in FP called "Higher order function"
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
    showStudents(getStudentsByCriterion(roster, (Student s) -> {
      return s.getCourses().size() < 3;
    }));
    // ??? compiler knows:
    // MUST BE AN OBJECT
    // MUST IMPLEMENT Criterion
    // Notices Criterion declares EXACTLY ONE abstract method (and it's an interface)
    //
    // WE MUST:
    // only be interested in implementing THAT ONE abstract method
    //
    // We end up with: an object, created from a nameless class (not stricly "anonymous"
    // because Java has a special "anonymous" syntax :)
//    showStudents(getStudentsByCriterion(roster, ???));

//    var x = "Hello";
    // cannot use var, because nobody knows that the evenutal type should be!
    // cannot use Object, a) rules, b) what method would we be implementing?
    Criterion thing = (Student s) -> {
      return s.getCourses().size() < 3;
    };

    System.out.println("thing instance of Object? " + (thing instanceof Object));
    System.out.println("thing instance of Criterion? " + (thing instanceof Criterion));

    System.out.println("Type of String is " + "thing".getClass().getName());
    System.out.println("Type of thing is " + thing.getClass().getName());
    Class<?> cl = thing.getClass();
    Method [] methods = cl.getMethods();
    for (Method m : methods) {
      System.out.println("> " + m);
    }

    Constructor[] ca = cl.getDeclaredConstructors();
    for (Constructor c : ca) {
      System.out.println(">> " + c);
    }

    Object thing2 = (Criterion)(Student s) -> {
      return s.getCourses().size() < 3;
    };
    System.out.println("thing2 instanceof Silly? " + (thing2 instanceof Silly));
    System.out.println("thing2 instanceof Criterion? " + (thing2 instanceof Criterion));

    // Can specify ALL arg types, maybe for readability, maybe for disambiguation
    // ALL or NONE types
//    Criterion thing3 = (Student s) -> {
    // IF UNAMBIGUOUS ARGS CAN USE VAR: supports annotation of the argument
    // ALL ARGS USE VAR, OR NONE DO
//    Criterion thing3 = (@Deprecated s) -> { // NO BAD
////    Criterion thing3 = (@Deprecated var s) -> { // YES OK
//    Criterion thing3 = (var s) -> { // YES OK
// EXACTLY ONE argument, and don't specify any type (explicit or var)
    // can leave off parens
//    Criterion thing3 = s -> {
//      return s.getCourses().size() < 3;
//    };

// IF BODY ONLY SAYS "RETURN <EXPRESSION>"
// replace entire body with <EXPRESSION>
    Criterion thing3 = s -> s.getCourses().size() < 3 ;

    showStudents(getStudentsByCriterion(roster,
        s -> s.getGrade() > 70 && s.getCourses().size() < 3));

  }
}
