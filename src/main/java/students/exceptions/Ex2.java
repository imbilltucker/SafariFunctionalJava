package students.exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.UnaryOperator;

class Either<F, S> {
  private F failure;
  private S success;

  public Either(F failure, S success) {
    this.failure = failure;
    this.success = success;
  }

  public boolean isSuccess() {
    return failure != null;
  }

  public boolean isFailure() {
    return failure == null;
  }
}

interface UnaryOpException<E> {
  E apply(E e) throws Throwable;

  static UnaryOperator<String> wrap(UnaryOpException<String> op) {
    return e -> {
      try {
        return op.apply(e);
      } catch (Throwable t) {
        return "**** ERROR that didn't work: " + t.getMessage();
      }
    };
  }
}

public class Ex2 {
  public static String applyAFunction(String s, UnaryOperator<String> op) {
    return "The result is " + op.apply(s);
  }

  public static String firstLineOfFile(String fn) throws IOException {
    try (BufferedReader br = Files.newBufferedReader(Path.of(fn));) {
      return br.readLine();
    }
  }

  public static void main(String[] args) {
    String s = "Hello";
    UnaryOperator<String> upper = st -> st.toUpperCase();
    String res = applyAFunction(s, upper);
    System.out.println("Result is " + res);

//    UnaryOperator<String> fileFirstLine = fn -> firstLineOfFile(fn);
    UnaryOperator<String> fileFirstLine = UnaryOpException.wrap(Ex2::firstLineOfFile);

    String res2 = applyAFunction("dat.txt", fileFirstLine);
    System.out.println(res2);
  }
}
