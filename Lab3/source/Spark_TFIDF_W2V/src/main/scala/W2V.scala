import java.io.File

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

import scala.io.Source
/**
  * Created by Mayanka on 19-06-2017.
  */
object W2V {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "C:\\Users\\putha\\Desktop\\KDM\\winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
      .set("spark.driver.memory", "6g").set("spark.executor.memory", "6g")

    val sc = new SparkContext(sparkConf)

    val input = sc.textFile("Input/tohoku.earthquake.tsunami.8.txt").map(line => line.split(" ").toSeq)

    val modelFolder = new File("tohokuNgram")
    val filename = "Input/N-gramOutput"

      if (modelFolder.exists()) {
        val sameModel = Word2VecModel.load(sc, "tohokuNgram")
        for (line <- Source.fromFile(filename).getLines) {
          val synonyms = sameModel.findSynonyms(line, 5)
          for ((synonym, cosineSimilarity) <- synonyms) {
            println(s"$synonym $cosineSimilarity")
          }
        }
      }

      else {
        val word2vec = new Word2Vec().setVectorSize(1000)

        val model = word2vec.fit(input)
        for (line <- Source.fromFile(filename).getLines) {
          val synonyms = model.findSynonyms(line, 5)

          for ((synonym, cosineSimilarity) <- synonyms) {
            println(s"$synonym $cosineSimilarity")
          }
        }

        model.getVectors.foreach(f => println(f._1 + ":" + f._2.length))

        // Save and load model
        model.save(sc, "tohokuNgram")

      }
    }

}
