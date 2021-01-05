package students;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

public class Student {
  private String name;
  private int grade;
  private List<String> courses;

  private Student(String name, int grade, List<String> courses) {
    this.name = name;
    this.grade = grade;
    this.courses = courses;
  }

  public static Student of(String name, int grade, String ... courses) {
    return new Student(name, grade, List.of(courses));
//    return new Student(name, grade,
//        Collections.unmodifiableList(Arrays.asList(courses.clone())));
  }

  public String getName() {
    return name;
  }

  public Student withName(String n) {
    return new Student(n, this.grade, this.courses);
  }

  public int getGrade() {
    return grade;
  }

  public Student withGrade(int grade) {
    return new Student(this.name, grade, this.courses);
  }

  public List<String> getCourses() {
    return courses;
  }

  public Student withAdditionalCourses(String ... courses) {
    List<String> newCourses = new ArrayList<>(Arrays.asList(courses));
    newCourses.addAll(List.of(courses));
    return new Student(this.name, this.grade, newCourses);
  }

  @Override
  public String toString() {
    return "Student{" +
        "name='" + name + '\'' +
        ", grade=" + grade +
        ", courses=" + courses +
        '}';
  }

  public static void main(String[] args) {
    Student s = Student.of("Fred", 76, "Math", "Physics");
    System.out.println(s);
    s = s.withName("Frederick");
    System.out.println(s);
    s = s.withGrade(82);
    System.out.println(s);
    s = s.withAdditionalCourses("Chemistry", "Astrophysics");
    System.out.println(s);
  }

  public boolean isSmart() {
    return this.grade > 70;
  }

  public static Criterion getSmartCriterion() {
    return (Student s) -> {
      return s.getGrade() > 70;
    };
  }

  // threshold must be final, or "effectively" final
  public static Predicate<Student> getSmartPredicate(/*final */int threshold) {
    // "closure"
    // take a copy of threshold and embedd in the "object" that "is" the lambda
    // a copy is valid IF and ONLY IF, neither ever change
//    threshold++;
    return s -> s.getGrade() > threshold; // Computed a behavior... where the result (behavior)
    // depends on an argument to this enclosing "behavior factory" :)
  }

  public static Predicate<Student> getEnthusiastic(int threshold) {
    return s -> s.getCourses().size() > threshold;
  }
}
