package SujiFile

import java.io.{BufferedWriter, FileOutputStream, OutputStreamWriter}

import org.apache.spark.{SparkConf, SparkContext}
import org.apache.log4j.{Level, Logger}
import openie.CoreNLP;

/**
  * Created by putha on 6/28/2017.
  */
object OpenIESuji {

 def main(args: Array[String]) {

    val sparkConfSuji = new SparkConf().setAppName("Open IE SUJI").setMaster("local[*]")

    val scSuji = new SparkContext(sparkConfSuji)

    Logger.getLogger("orgSuji").setLevel(Level.OFF)
    Logger.getLogger("akkaSuji").setLevel(Level.OFF)
   val fileWriteSuji = "OutputFileSuji/OpenIETriplet.txt"
   val FilewriterSuji = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileWriteSuji)))

    val inputFIleSUji = scSuji.textFile("dataSuji/JapanEarthQuake").map(lineSuji => {


      val tripletSuji=CoreNLPSuji.returnTriplets(lineSuji)
      tripletSuji
    })

   val outputFileSUji=inputFIleSUji.collect().mkString;
   FilewriterSuji.write(outputFileSUji);
   FilewriterSuji.close();




 }

}

