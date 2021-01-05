package students.exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.Function;
import java.util.function.UnaryOperator;

class Either<F, S> {
  private F failure;
  private S success;

  public Either(F failure, S success) {
    this.failure = failure;
    this.success = success;
  }

  public boolean isSuccess() {
    return failure == null;
  }

  public boolean isFailure() {
    return failure != null;
  }

  public S getSuccess() {
    if (isFailure()) throw new IllegalStateException("Can't get success from a failure");
    return success;
  }
  public F getFailure() {
    if (isSuccess()) throw new IllegalStateException("Can't get failure from a success");
    return failure;
  }
}

// *** Use a Function rather than a UnaryOperator, allowing different return type
interface FunctionException<A, R> {
  R apply(A e) throws Throwable;

  static <A, R>Function<A, Either<String, R>> wrap(FunctionException<A, R> op) {
    return a -> {
      try {
        return new Either<>(null, op.apply(a));
      } catch (Throwable t) {
        return new Either<>("**** ERROR that didn't work: " + t.getClass().getName() + ", " + t.getMessage(), null);
      }
    };
  }
}

interface UnaryOpException<E> {
  E apply(E e) throws Throwable;

  static UnaryOperator<String> wrap(UnaryOpException<String> op) {
    return e -> {
      try {
        return op.apply(e);
      } catch (Throwable t) {
        return "**** ERROR that didn't work: " + t.getClass().getName() + ", " + t.getMessage();
      }
    };
  }
}

public class Ex2 {
  public static String applyAFunction(String s, UnaryOperator<String> op) {
    return "The result is " + op.apply(s);
  }

  // This function applier has to remain generic
  // It cannot decide that it's returning exactly a String, because
  // it might be an "R" (which might, in turn, be an Either of something)
  public static <A, R> R applyAFunction(A s, Function<A, R> op) {
    return op.apply(s);
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

    String res2 = applyAFunction("data.txt", fileFirstLine);
    System.out.println(res2);

    Function<String, Either<String, String>> fileFirstLineFn =
        FunctionException.wrap(Ex2::firstLineOfFile);

    // Our new function doesn't return a String it returns an Either either
    // containing the result, or containing the error. This bubbles up to
    // the caller which must handle it (that's the same thing that exceptions
    // do, of course--depend on the caller to handle them :)
    Either<String, String> eitherResult = applyAFunction("data.txt", fileFirstLineFn);
    if (eitherResult.isSuccess()) {
      System.out.println("That worked: " + eitherResult.getSuccess());
    } else {
      System.out.println("That failed: " + eitherResult.getFailure());
    }
  }
}
