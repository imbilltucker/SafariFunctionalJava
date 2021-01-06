package superiterable;

import java.util.List;

public class UseLazyFunctor {
  public static void main(String[] args) {
    LazyFunctor<String> lf = new LazyFunctor<>(List.of("Fred", "Jim", "Sheila"));
    LazyFunctor<String> lf2 = lf.map(String::toUpperCase);
    lf2.forEach(System.out::println);

  }
}
