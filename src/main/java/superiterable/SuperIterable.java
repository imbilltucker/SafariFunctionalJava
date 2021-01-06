package superiterable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class SuperIterable<E> implements Iterable<E> {
  private Iterable<E> self;

  public SuperIterable(Iterable<E> self) {
    this.self = self;
  }

  public SuperIterable<E> filter(Predicate<E> crit) {
    List<E> res = new ArrayList<>();
    for (E s : self) {
      if (crit.test(s)) {
        res.add(s);
      }
    }
    return new SuperIterable<>(res);
  }

  // a map method on a "wrapper-like" bucket of data is called "Functor"
  public <F> SuperIterable<F> map(Function<E, F> op) {
    List<F> res = new ArrayList<>();
    for (E s : self) {
      res.add(op.apply(s)); // NOT!!!! op(s)
    }
    return new SuperIterable<>(res);
  }

  // Functional programming calls a "wrapper-like" thing with a flatMap method....
  // Monad
  public <F> SuperIterable<F> flatMap(Function<E, SuperIterable<F>> op) {
    List<F> res = new ArrayList<>();
    for (E s : self) {
      for (F f : op.apply(s)) {
        res.add(f);
      }
    }
    return new SuperIterable<>(res);
  }

  public SuperIterable<E> peek(Consumer<E> op) {
    for (E e : self) {
      op.accept(e);
    }
    return this;
  }

  // "forEach"
//  public void doToAll(Consumer<E> op) {
//    for (E s : this.self) {
//      op.accept(s);
//    }
//  }

  @Override
  public Iterator<E> iterator() {
    return self.iterator();
  }
}
