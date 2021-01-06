package concordancespark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;
import java.util.regex.Pattern;

public class Concordance {
  private static final Pattern WORD_BOUNDARY = Pattern.compile("\\W+");

  public static void main(String[] args) {
    SparkConf conf = new SparkConf()
        .setAppName("concordace")
        .setMaster("local");
    JavaSparkContext context = new JavaSparkContext(conf);

    // open file like this, file MUST BE on all cluster nodes
    context.textFile("PrideAndPrejudice.txt")
        .map(String::toLowerCase)
        .flatMap(l -> Arrays.asList(WORD_BOUNDARY.split(l)).iterator())
        .filter(w -> w.length() > 0)
        .mapToPair(w -> new Tuple2<>(w, 1L))
        .aggregateByKey(0L, (a, b) -> a + b, (a, b) -> a + b)
        .mapToPair(t -> new Tuple2<>(t._2, t._1))
        .sortByKey(false)
        .map(t -> String.format("%20s : %5d", t._2, t._1))
        .take(200)
        .forEach(System.out::println);

//        .collect()
//        .forEach(System.out::println);

    context.close();

  }
}
