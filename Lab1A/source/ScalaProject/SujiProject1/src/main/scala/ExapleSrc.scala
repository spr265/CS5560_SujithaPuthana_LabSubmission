/**
  * Created by putha on 6/13/2017.
  */
import java.io._

object ExapleSrc {
  def main(args: Array[String]) {
    val writeFile = new PrintWriter(new File("SujiScalaTest.txt" ))

    writeFile.write("This is a sample scala Program")
    writeFile.close()
  }
}

