package students.streams;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

class Average {
  private double sum;
  private long count;

  public Average(double sum, long count) {
    this.sum = sum;
    this.count = count;
  }

  public Average include(double d) {
    return new Average(this.sum + d, this.count + 1);
  }

  public Average merge(Average other) {
    return new Average(this.sum + other.sum, this.count + other.count);
  }

  public Optional<Double> get() {
    if (count != 0) {
      return Optional.of(this.sum / this.count);
    } else {
      return Optional.empty();
    }
  }
}

public class Averager1 {
  public static void main(String[] args) {
//    DoubleStream.generate(() -> Math.random())
//        .limit(1000)
//        .mapToObj(v -> new Average(v, 1))
//        .reduce((a1, a2) -> a1.merge(a2))
//        .ifPresent(a -> a.get().ifPresent(v -> System.out.println("The average is " + v)));
//    DoubleStream.generate(() -> Math.random())
//        .limit(1000)
//        .mapToObj(v -> new Average(v, 1))
//        .reduce(new Average(0, 0), (a1, a2) -> a1.merge(a2))
//        .get()
//        .ifPresent(v -> System.out.println("The average is " + v));
    long start = System.nanoTime();
    ThreadLocalRandom.current().doubles()
//    DoubleStream.iterate(0.0, x -> ThreadLocalRandom.current().nextDouble())
        .limit(1_000_000_000)
//        .unordered()
        .parallel()
//        .sequential()
//        .mapToObj(v -> v)
        .boxed()
        .reduce(new Average(0, 0), (a, d) -> a.include(d), (a1, a2) -> a1.merge(a2))
        .get()
        .ifPresent(System.out::println);
    long time = System.nanoTime() - start;
    System.out.println("computation took " + (time / 1_000_000_000.0) + " seconds");
  }
}
