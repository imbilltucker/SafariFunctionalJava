package verify;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;

import java.util.List;

public class Verify {
  public static void main(String[] args) {
    SparkConf conf = new SparkConf().setAppName("verify").setMaster("local");
    JavaSparkContext context = new JavaSparkContext(conf);
    String res = context.parallelize(List.of("Hello, ", "Spark ", "World", "!"))
        .reduce((a,b) -> a + b);
    System.out.println(res);
  }
}
