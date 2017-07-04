package openie

import java.io.{BufferedWriter, FileOutputStream, OutputStreamWriter}

import org.apache.log4j.{Level, Logger}
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Mayanka on 27-Jun-16.
  */
object SparkOpenIE {

  def main(args: Array[String]) {
    // Configuration
    val sparkConf = new SparkConf().setAppName("SparkWordCount").setMaster("local[*]")

    val sc = new SparkContext(sparkConf)


    // Turn off Info Logger for Console
    Logger.getLogger("org").setLevel(Level.OFF)
    Logger.getLogger("akka").setLevel(Level.OFF)

    val fileWriteSuji = "OutputFileSuji/OpenIETriplet.txt"
    val Filewriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileWriteSuji)))

    val input = sc.textFile("InputDataSuji/tohoku.earthquake.tsunami.3.txt").map(line => {
      //Getting OpenIE Form of the word using lda.CoreNLP

      val t=CoreNLP.returnTriplets(line)
      t
      //Filewriter.write(input.collect().mkString + "\n")
    })
    //Filewriter.close();

//    println(CoreNLP.returnTriplets("The dog has a lifespan of upto 10-12 years."))
    println(input.collect().mkString("\n"))
    val output=input.collect().mkString;
    Filewriter.write(output);
    Filewriter.close();




  }

}
