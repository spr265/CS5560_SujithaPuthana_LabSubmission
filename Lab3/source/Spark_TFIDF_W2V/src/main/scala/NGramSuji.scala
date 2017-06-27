import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by putha on 6/21/2017.
  */
object NGramSuji {

  def main(args: Array[String]): Unit = {
   val asuji = getNGrams(" The dog saw John in the park.The little bear saw the fine fat trout in the rocky brook.The dog started chasing John.The little bear caught a fish in the rocky brook.",2 )
/*
    val sparkConfSUji = new SparkConf().setAppName("TermFrequency - IDF").setMaster("local[*]")

    val scSuji = new SparkContext(sparkConfSUji)

    val asuji = scSuji.textFile("Input/tohoku.earthquake.tsunami.8.txt",2)
*/
    asuji.foreach(fvalsuji=>println(fvalsuji.mkString(" ")))


  }

  def getNGrams(sentencesuji: String, nsuji:Int): Array[Array[String]] = {
    val wordsuji = sentencesuji
    val ngramsSuji = wordsuji.split(' ').sliding(nsuji)
    ngramsSuji.toArray
  }


}


