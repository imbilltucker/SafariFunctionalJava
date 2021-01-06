package students.streams;

import java.util.Optional;
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
    DoubleStream.generate(() -> Math.random())
        .limit(1000)
        .mapToObj(v -> new Average(v, 1))
        .reduce((a1, a2) -> a1.merge(a2))
        .ifPresent(a -> a.get().ifPresent(v -> System.out.println("The average is " + v)));
  }
}
