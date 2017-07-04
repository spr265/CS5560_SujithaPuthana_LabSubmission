package SujiFile

import org.apache.spark.{SparkConf, SparkContext}
import rita.RiWordNet

/**
  * Created by putha on 6/28/2017.
  */
object wordSparkNetSuji {
def main(args: Array[String]): Unit = {
    System.setProperty("hadoopSuji.homeLocal.directory.Path", "C:\\Users\\putha\\Desktop\\KDM\\winutils")

    val confSuji = new SparkConf().setAppName("wordSparkNetSuji").setMaster("local[*]").set("sparkSuji.driverSuji.memorySuji", "4g").set("sparkSuji.driverSuji.memorySuji", "4g")
    val scSuji = new SparkContext(confSuji)


    val data=scSuji.textFile("data/sample")

    val ddSuji=data.map(funSuji=>{
      val wordnet = new RiWordNet("C:\\Users\\putha\\Desktop\\KDM\\Lab4\\WordNet-3.0")
      val farrSuji=funSuji.split(" ")
      getSynoymnsSuji(wordnet,"power")
    })
  ddSuji.take(1).foreach(funSuji=>println(funSuji.mkString("\n")))
  }

  def getSynoymnsSuji(wordnetSuji:RiWordNet,wordSuji:String): Array[String] ={
    println(wordSuji)
    val posSuji=wordnet.getPos(wordSuji)
    println(posSuji.mkString(" "))
    val synsuji=wordnet.getAllSynonyms(wordSuji, pos(0), 10)
    synsuji
  }

}