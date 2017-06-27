import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import java.util.List;

/**
 * Created by putha on 6/21/2017.
 */
public class coreNlpSuj {

    public static String returnLemma(String sentenceSuji) {

        Document docSuji = new Document(sentenceSuji);
        String lemmaSuji="";
        for (Sentence sentSuji : docSuji.sentences()) {
            List<String> listsuji=sentSuji.lemmas();
            for (int isuji = 0; isuji < listsuji.size() ; isuji++) {
                lemmaSuji+= listsuji.get(isuji) +" ";
            }
            System.out.println(lemmaSuji);
        }

        return lemmaSuji;
    }

}

