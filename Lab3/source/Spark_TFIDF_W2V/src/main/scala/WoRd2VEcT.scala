import java.io.File

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{Word2Vec, Word2VecModel}

/**
  * Created by putha on 6/21/2017.
  */
object WoRd2VEcT {

  def main(args: Array[String]): Unit = {

    System.setProperty("hadoopsuji.homelocal.dirsuji", "C:\\Users\\putha\\Desktop\\KDM\\winutils")

    val sparkConfSuji = new SparkConf().setAppName("Word to Vector").setMaster("local[*]")
      .set("sparkSuji.driverSUj.memoryLocal", "9g").set("sparkSuji.executorSuji.memorySuji", "9g")

    val scSuji = new SparkContext(sparkConfSuji)

    val inputSuji = scSuji.textFile("data/PS3").map(lineSuji => lineSuji.split(" ").toSeq)

    val modelFolderSuji = new File("PS3dic")

    if (modelFolderSuji.exists()) {
      val sameModelSuji = Word2VecModel.load(scSuji, "PS3dic")
      val synonymsSuji = sameModelSuji.findSynonyms("The", 2)

      for ((synonymsSuji, cosineSimilaritySuji) <- synonymsSuji) {

        println(s"$synonymsSuji $cosineSimilaritySuji")

      }
    }

    else {
      val word2vecSuji = new Word2Vec().setVectorSize(1000)

      val modelSuji = word2vecSuji.fit(inputSuji)

      val synonymsSuji = modelSuji.findSynonyms("The", 2)

      for ((synonymSuji, cosineSimilaritySuji) <- synonymsSuji)
      {
        //
        println(s"$synonymSuji $cosineSimilaritySuji")
      }

      modelSuji.getVectors.foreach(fSj => println(fSj._1 + ":" + fSj._2.length))

      //loading the file from saved sttus
      modelSuji.save(scSuji, "PS3dic")

    }

  }
}
