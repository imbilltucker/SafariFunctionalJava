package students.exceptions;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.function.UnaryOperator;

public class Ex1 {
  public static String applyAFunction(String s, UnaryOperator<String> op) {
    return "The result is " + op.apply(s);
  }

//  public static String firstLineOfFile(String fn) throws IOException  {
  public static String firstLineOfFile(String fn) {
    // Java 8 uses "Paths.get"
    try (BufferedReader br = Files.newBufferedReader(Path.of(fn));) {

      return br.readLine();
    } catch (Throwable t) {
//      throw new RuntimeException(t);
      return "The file " + fn + " caused a problem " + t.getMessage();
    }
  }

  public static void main(String[] args) {
    String s = "Hello";
    UnaryOperator<String> upper = st -> st.toUpperCase();
    String res = applyAFunction(s, upper);
    System.out.println("Result is " + res);

//    UnaryOperator<String> fileFirstLine = fn -> firstLineOfFile(fn);
    UnaryOperator<String> fileFirstLine = Ex1::firstLineOfFile;

    String res2 = applyAFunction("dat.txt", fileFirstLine);
    System.out.println(res2);
  }
}
