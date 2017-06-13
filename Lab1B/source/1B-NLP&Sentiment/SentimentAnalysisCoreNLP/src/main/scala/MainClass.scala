/**
 * Created by Mayanka on 23-Jul-15.
 */
object MainClass {

  def main(args: Array[String]) {
    val sentimentAnalyzer: SentimentAnalyzer = new SentimentAnalyzer
    val TextFile = scala.io.Source.fromFile("src\\main\\Data\\ParisAttack2015").mkString
    val TextFileSentiment: TextFileSentiment = sentimentAnalyzer.findSentiment(TextFile)
    System.out.println(TextFileSentiment)
  }
}
