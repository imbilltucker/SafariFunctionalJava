package concordancestream;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Concordance {
  private static final Pattern WORD_BOUNDARY = Pattern.compile("\\W+");
  private static final Comparator<Map.Entry<String, Long>> normalOrder = Map.Entry.comparingByValue();
  private static final Comparator<Map.Entry<String, Long>> reverseOrder = normalOrder.reversed();

  public static void main(String[] args) throws Throwable {
    try (Stream<String> in = Files.lines(Path.of("PrideAndPrejudice.txt"))) {
      in
//          .flatMap(l -> WORD_BOUNDARY.splitAsStream(l))
          .map(String::toLowerCase) // the = The
          .flatMap(WORD_BOUNDARY::splitAsStream)
          .filter(w -> w.length() > 0)
//          .collect(Collectors.groupingBy(w -> w, Collectors.counting()))
          .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
          .entrySet().stream()
//          .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
//          .sorted(reverseOrder)
          .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
          .limit(200)
          .map(e -> String.format("%20s : %5d", e.getKey(), e.getValue()))
          .forEach(System.out::println);
    }
  }
}
