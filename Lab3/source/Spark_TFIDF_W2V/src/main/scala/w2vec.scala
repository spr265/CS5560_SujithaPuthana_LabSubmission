/**
  * Created by putha on 6/26/2017.
  */

import java.io.File

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

object w2vec {
  def main(args: Array[String]): Unit = {

    System.setProperty("hadoop.home.dir", "C:\\Users\\putha\\Desktop\\KDM\\winutils")

    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")
      .set("spark.driver.memory", "6g").set("spark.executor.memory", "6g")

    val sc = new SparkContext(sparkConf)

    val input = sc.textFile("data/InputFileSuji/ParisAttacks2015 (21).txt").map(line => line.split(" ").toSeq)

    val word2vec = new Word2Vec()

    val model = word2vec.fit(input)

    val synonyms = model.findSynonyms("Paris", 20)

    for ((synonym, cosineSimilarity) <- synonyms) {
      println(s"$synonym $cosineSimilarity")
    }

    // Save and load model
    model.save(sc, "myModelPath1")
    val sameModel = Word2VecModel.load(sc, "myModelPath1")

  }

}