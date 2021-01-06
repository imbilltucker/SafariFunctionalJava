package students.streams;

import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class Lazy {
  public static void main(String[] args) {
    DoubleStream.generate(() -> Math.random())
        .peek(System.out::println)
        .allMatch(v -> v > 0.9);
//        .forEach(System.out::println);
  }
}
