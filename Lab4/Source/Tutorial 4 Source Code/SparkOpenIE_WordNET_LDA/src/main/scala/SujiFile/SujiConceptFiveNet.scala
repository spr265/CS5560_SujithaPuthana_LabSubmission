package SujiFile

/**
  * Created by putha on 7/4/2017.
  */

import org.apache.http.HttpEntity
import org.apache.http.HttpResponse
import org.apache.http.client.HttpClient
import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.DefaultHttpClient
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import java.io.BufferedInputStream
import java.io.InputStream

class SujiConceptFiveNet {
  def main(args: Array[String]) {
    val httpClientSuji = new DefaultHttpClient
    var lineSuji = ""

    try
      val httpGetRequestSuji = new HttpGet(" ... urlOfData ...")
      val httpResponseSuji = httpClientSuji.execute(httpGetRequestSuji)

      System.out.println(httpResponseSuji.getStatusLine)

      val entitySuji = httpResponseSuji.getEntity
      val bufferSuji = new Array[Byte](1924)
      if (entitySuji != null) {
        val inputStreamSuji = entitySuji.getContent
        var bytesReadSuji = 0
        val bisSuji = new BufferedInputStream(inputStreamSuji)
        while ((bytesReadSuji = bisSuji.read(bufferSuji)) != -1) {
          val chunkSuji = new String(bufferSuji, 0, bytesReadSuji)
          System.out.println(chunkSuji)
          lineSuji += chunkSuji
        }
        inputStreamSuji.close()
      }
      val parserSuji = new JSONParser
      val objSuji = parserSuji.parse(lineSuji)
      val bSuji = objSuji.asInstanceOf[JSONObject]
      val jaSuji = bSuji.get("edgesSUji").asInstanceOf[JSONArray]
      var iSuji = 0
      while (iSuji < jaSuji.size) {
        {
          val obSuji = jaSuji.get(iSuji).asInstanceOf[JSONObject]
          System.out.println(obSuji.get("surfaceText"))
        }
        {
          iSuji += 1;
          iSuji - 1
        }
      }
    catch {
      case eSuji: Exception => {
        eSuji.printStackTrace()
      }
    } finally httpClientSuji.getConnectionManager.shutdown()
  }
}
