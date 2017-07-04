package SujiFile

/**
  * Created by putha on 6/28/2017.
  */

import java.io.PrintStream

import org.apache. log4j.{Level, Logger}
import org.apache.spark.mllib.clustering.{DistributedLDAModel, EMLDAOptimizer, LDA, OnlineLDAOptimizer}
import org.apache.spark.mllib.feature.{HashingTF, IDF}
import org.apache.spark.mllib.linalg.Vector
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}
import scopt.OptionParser
import lda.CoreNLP
object IDASuji {

  private case class Paramssuji(
                                 inputSuji: Seq[String] = Seq.empty,
                                 I: Int = 60,
                                 algorithmSuji: String = "emu")

  def main(args: Array[String]) {
    val defaultParamsSuji = Paramssuji()

    val parserSuji = new OptionParser[Paramssuji]("Example LDA") {
      head("LDASujiExample: this is the example of LDA application for text data.")

      opt[Int]("ok")
        .text(s"Here are topic number. default: ${defaultParamsSuji.I}")
        .action((i,s) => s.copy(I = i))

      opt[String]("SujiAlgorithm")
        .text ( s"Using Interface Algorithm. emu .It supports online." +
          s" default: ${defaultParamsSuji.algorithmSuji}")
        .action((i, s) => s.copy(algorithmSuji = i))

      arg[String] ("...<inputSuji>...")
        //Declaring Input Paths
        .text("inputing the paths (Sujidirectories)." +
          "  Every data file line will hold only one document.")

        //using the unbound
        .unbounded ()
        //adding the require
        .required ()
        .action((x, c) => c.copy(inputSuji = c.inputSuji :+ x))
    }

    parserSuji.parse(args, defaultParamsSuji).map { paramsSuji =>
      runSuji(paramsSuji)
    }
      .getOrElse
    {
      parserSuji.showUsageAsError

      sys.exit(1)
    }
  }

  private def runSuji(paramsSuji: Params) {
    System.setProperty("hadoopSuji.homeLocal.directory.Path", "C:\\Users\\putha\\Desktop\\KDM\\winutils")
    val confSuji = new SparkConf().setAppName(s"SujiLDAExample with $paramsSuji").setMaster("local[*]").set("sparkSuj.driverSuji.memory", "9g").set("sparkSuji.executorSuji.memory", "9g")

    val scSuji = new SparkContext(confSuji)

    Logger.getRootLogger.setLevel (Level.WARN)

    val topicSuji_outputSuji = new PrintStream("dataSuji/ResultsSuji.txt")

    val preprocessStartSuji = System.nanoTime()
    val (corpusSuji, vocabArraySuji, actualNumTokensSuji)=preprocessSuji(scSuji,paramsSuji.input)
    corpusSuji.cache()
    val actualCorpusSizeSuji = corpusSuji.count()
    val actualVocabSizeSuji = vocabArraySuji.length

    val preprocessElapsedSuji = (System.nanoTime() - preprocessStartSuji) / 1e9

    println ( "Printing the output below")
    println(s"Check below for corpus output:")
    println(s"\t Size of Training set : $actualCorpusSizeSuji documents")
    println(s"\t Size of Vocabulary : $actualVocabSizeSuji terms")
    println(s"\t Size of Training set : $actualNumTokensSuji tokens")
    println(s"\t Time for Preprocessing : $preprocessElapsedSuji sec")
    println()

    topicSuji_outputSuji.println()
    topicSuji_outputSuji.println(s"Printimg summary of Corpus :")
    topicSuji_outputSuji.println(s"\t Size of Training set : $actualCorpusSizeSuji documents")
    topicSuji_outputSuji.println(s"\t Size of Vocabulary : $actualVocabSizeSuji terms")
    topicSuji_outputSuji.println(s"\t Size of Training set : $actualNumTokensSuji tokens")
    topicSuji_outputSuji.println(s"\t Time  Preprocessing : $preprocessElapsedSuji sec")
    topicSuji_outputSuji.println()

    val ldaSuji = new LDA()

    val optimizerSuji = paramsSuji.algorithm.toLowerCase match {
      case "emu" => new EMLDAOptimizer

      case "onlineSuji" =>
        new OnlineLDAOptimizer().setMiniBatchFraction(0.05 + 1.0 / actualCorpusSizeSuji)

      case _ => throw new IllegalArgumentException(
        s"Only em, supports online but we got ${paramsSuji.algorithm}.")
    }

    ldaSuji.setOptimizer(optimizerSuji)
      .setK(paramsSuji.k)
      .setMaxIterations(30)

    val startTimeSuji = System.nanoTime()
    val ldaModelSuji = ldaSuji.run(corpusSuji)
    val elapsedSuji = (System.nanoTime() - startTimeSuji) / 1e9


    println( s" \t Training time: $elapsedSuji sec")


    topicSuji_outputSuji.println(s"Finished training LDA model.  Summary:")
    topicSuji_outputSuji.println(s"\t Training time: $elapsedSuji sec")

    if (ldaModelSuji.isInstanceOf[DistributedLDAModel]) {
      val distLDAModelSuji = ldaModelSuji.asInstanceOf[DistributedLDAModel]

      val avgLogLikelihoodSUji = distLDAModelSuji.logLikelihood / actualCorpusSizeSuji.toDouble

      topicSuji_outputSuji.println(s"\t : $avgLogLikelihoodSUji")
      topicSuji_outputSuji.println()
    }


    val topicIndicesSuji = ldaModelSuji.describeTopics(maxTermsPerTopic = actualVocabSizeSuji)
    val topicsSuji = topicIndicesSuji.map { case (termsSuji, termWeightsSUji) =>
      termsSuji.zip(termWeightsSUji).map { case (termSuji, weightSuji) => (vocabArraySuji(termSuji.toInt), weightSuji) }
    }
    println(s"${paramsSuji.k} HERE TOPICS :")
    topicSuji_outputSuji.println(s"${paramsSuji.k} topicsSuji:")
    topicsSuji.zipWithIndex.foreach { case (topicSuji, j) =>
      println(s"TOPIC $j")

      topicsSuji.foreach { case (termSuji, weightSuji) =>
        println(s"$termSuji\t$weightSuji")
        topicSuji_outputSuji.println(s"TOPIC_$j;$termSuji;$weightSuji")
      }

      topicSuji_outputSuji.println()
    }
    topicSuji_outputSuji.close()
    scSuji.stop()
  }

  private def preprocessSuji(scSuji: SparkContext,pathsSuji: Seq[String]):
  (RDD[(Long, Vector)],
    Array[String], Long) =
  {

    val stopWordsSUji=scSuji.textFile("dataSuji/stopwordsSuji.txt").collect()
    val stopWordsBroadCastSUji=scSuji.broadcast(stopWordsSUji)

    val dfSuji = scSuji.textFile(pathsSuji.mkString(",")).map(fSuji => {
      val lemmatisedSuji=CoreNLP.returnLemma(fSuji)
      val splitStringSuji = lemmatisedSuji.split(" ")
      splitStringSuji
    })


    val stopWordRemovedDFSuji=dfSuji.map(fSuji=>{

      val filteredFSuji=fSuji.map(_.replaceAll("[^a-zA-Z]"," "))

        .filter(ffSuji=>{
          if(stopWordsBroadCastSUji.value.contains(ffSuji.toLowerCase))
            false
          else
            true
        })
      filteredFSuji
    })

    val dfseqSuji=stopWordRemovedDFSuji.map(_.toSeq)

    val hashingTFSuji = new HashingTF(stopWordRemovedDFSuji.count().toInt)

    val tfSuji = hashingTFSuji.transform(dfseqSuji)
    tfSuji.cache()

    val idfSuji = new IDF().fit(tfSuji)


    val tfidfSuji = idfSuji.transform(tfSuji).zipWithIndex().map(_.swap)

    val dffSuji= stopWordRemovedDFSuji.flatMap(fSuji=>fSuji)
    val vocabSuji=dffSuji.distinct().collect()
    (tfidfSuji, vocabSuji, dffSuji.count())
  }
}

