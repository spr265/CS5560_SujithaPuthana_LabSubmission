package wordnet

import java.io.{BufferedWriter, FileOutputStream, OutputStreamWriter}

import org.apache.spark.{SparkConf, SparkContext}
import rita.RiWordNet

/**
  * Created by puthana on 07-03-2017.
  */
object WordNetSpark {
  def main(args: Array[String]): Unit = {
    System.setProperty("hadoop.home.dir", "C:\\Users\\putha\\Desktop\\KDM\\winutils")
    val conf = new SparkConf().setAppName("WordNetSpark").setMaster("local[*]").set("spark.driver.memory", "4g").set("spark.executor.memory", "4g")
    val sc = new SparkContext(conf)

    val fileWriteSuji = "OutputFileSuji/Synonym_Power.txt"
    val Filewriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileWriteSuji)))

    val data=sc.textFile("InputDataSuji/tohoku.earthquake.tsunami.3.txt")

    val dd=data.map(f=>{
      val wordnet = new RiWordNet("C:\\Users\\putha\\Desktop\\KDM\\Lab4\\WordNet-3.0")
      val farr=f.split(" ")
      getSynoymns(wordnet,"power")
    })
    dd.take(1).foreach(f=>println(f.mkString(" ")))
    //dd.saveAsTextFile("OutputFileSuji/Synonym_Power.txt");
  }
  def getSynoymns(wordnet:RiWordNet,word:String): Array[String] ={
    println(word)
    val pos=wordnet.getPos(word)
    println(pos.mkString(" "))
    val syn=wordnet.getAllSynonyms(word, pos(0), 10)
    syn
  }

}
