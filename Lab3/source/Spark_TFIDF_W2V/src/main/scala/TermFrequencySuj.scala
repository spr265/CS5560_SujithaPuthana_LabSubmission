import org.apache.spark.{SparkConf, SparkContext}
import org.apache.spark.mllib.feature.{HashingTF, IDF}

import scala.collection.immutable.HashMap

/**
  * Created by putha on 6/21/2017.
  */
object TermFrequencySuj {

  def main(args: Array[String]): Unit = {

    System.setProperty("hadoopsuji.homelocal.dirsuji", "C:\\Users\\putha\\Desktop\\KDM\\winutils")

    val sparkConfSUji = new SparkConf().setAppName("TermFrequency - IDF").setMaster("local[*]")

    val scSuji = new SparkContext(sparkConfSUji)

    val documentsSuji = scSuji.textFile("data/doc1Ngram")

    val documentseqSuji = documentsSuji.map(fvalSuji => {

      //defining the lemma process
      val lemmatisedSUji = CoreNLP.returnLemma(fvalSuji)

      //splitting the content with spacing
      val splitStringSuji = fvalSuji.split(" ")
      splitStringSuji.toSeq
    }
    )

    val hashingTFSuji = new HashingTF()


    val tfSuji = hashingTFSuji.transform(documentseqSuji)
    tfSuji.cache()


    val idfSuji = new IDF().fit(tfSuji)

    //Inverse file document creation
    val tfidfSuji = idfSuji.transform(tfSuji)

    val tfidfvaluesSuji = tfidfSuji.flatMap(fSuji => {
      //array discription
      val ffSuji: Array[String] = fSuji.toString.replace(",[", ";").split(";")
      //replace set
      val valuesSuji = ffSuji(2).replace("]", "").replace(")", "").split(",")
      valuesSuji
    })

    val tfidfindexSuji = tfidfSuji.flatMap(fSuji => {
      val ffSuji: Array[String] = fSuji.toString.replace(",[", ";").split(";")
      val indicesSuji = ffSuji(1).replace("]", "").replace(")", "").split(",")
      indicesSuji
    })

    tfidfSuji.foreach(fSuji => println(fSuji))

    val tfidfDataSuji = tfidfindexSuji.zip(tfidfvaluesSuji)

    var hmSuji = new HashMap[String, Double]

    tfidfDataSuji.collect().foreach(fSuji => {
      hmSuji += fSuji._1 -> fSuji._2.toDouble
    })

    val mappSuji = scSuji.broadcast(hmSuji)

    val documentDataSuji = documentseqSuji.flatMap(_.toList)
    val ddSuji = documentDataSuji.map(fSuji => {
      val iSuji = hashingTFSuji.indexOf(fSuji)
      val hSuji = mappSuji.value
      (fSuji, hSuji(iSuji.toString))
    })

    val dd1Suji = ddSuji.distinct().sortBy(_._2, false)

    println("Displaying the Term Frequency Inverse Document Frequency(TF-IDF) for the given input")
    dd1Suji.take(20).foreach(fSuji => {

      println(fSuji)

    })

    dd1Suji.saveAsTextFile("OutputFileSuji/doc1NGram");

  }

}
