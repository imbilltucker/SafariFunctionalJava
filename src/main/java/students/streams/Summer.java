package students.streams;

import java.util.OptionalInt;
import java.util.stream.IntStream;

public class Summer {
  public static void main(String[] args) {
    OptionalInt sum = IntStream.iterate(1, x -> x + 1)
        .limit(10)
        .reduce((a, b) -> a + b);
//        .forEach(System.out::println);
    sum.ifPresent(System.out::println);
    int sum2 = IntStream.iterate(1, x -> x + 1)
        .limit(10)
        .reduce(0, (a, b) -> a + b);
//        .forEach(System.out::println);
    System.out.println(sum2);
  }
}
