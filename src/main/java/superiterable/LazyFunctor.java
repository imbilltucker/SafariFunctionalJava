package superiterable;

import java.util.Iterator;
import java.util.function.Function;

public class LazyFunctor<E> implements Iterable<E> {
  private Iterable<E> self;

  public LazyFunctor(Iterable<E> self) {
    this.self = self;
  }

  public <F> LazyFunctor<F> map(Function<E, F> op) {
    return new LazyFunctor<F>(new Iterable<F>() {
      private Iterator<E> originalIterator = self.iterator();
      @Override
      public Iterator<F> iterator() {
        return new Iterator<F>(){

          @Override
          public boolean hasNext() {
            System.out.println("hasNext()");
            return originalIterator.hasNext();
          }

          @Override
          public F next() {
            E e = originalIterator.next();
            System.out.println("doing calculation on " + e);
            return op.apply(e);
          }
        };
      }
    });
  }

  @Override
  public Iterator<E> iterator() {
    return self.iterator();
  }
}
