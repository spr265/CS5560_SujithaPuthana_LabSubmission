package classification;

import edu.stanford.nlp.simple.Document;
import edu.stanford.nlp.simple.Sentence;

import java.util.List;

/**
 * Created by putha on 7/11/2017.
 */
public class WordSplit {
    public static String returnword(String sentence) {

        Document doc = new Document(sentence);
        String word="";
        for (Sentence sent : doc.sentences()) {  // Will iterate over two sentences

            List<String> l=sent.words();
            for (int i = 0; i < l.size() ; i++) {
                word+= l.get(i) +" ";
            }
            //   System.out.println(lemma);
        }

        return word;
    }

}
