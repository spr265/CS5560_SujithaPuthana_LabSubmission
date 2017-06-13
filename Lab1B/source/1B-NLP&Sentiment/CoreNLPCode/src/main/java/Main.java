import edu.stanford.nlp.hcoref.CorefCoreAnnotations;
import edu.stanford.nlp.hcoref.data.CorefChain;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations;
import edu.stanford.nlp.trees.Tree;
import edu.stanford.nlp.trees.TreeCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;


/**
 * Created by Sujitha on 13-Jun-17.
 */
public class Main {
    public static void main(String args[]) throws IOException {
        // creates a object StanfordCoreNLP , with NER,lemmatization,Parts of speech tagging, parsing and the coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

// Read text from file
        //String text = "This is text for processing.";
        String text = readText("src/main/Data/ParisAttacks2015.txt");

// create an Annotation empty for text
        Annotation document = new Annotation(text);

//run annotation
        pipeline.annotate(document);


        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            // the words traversing in the sentence
            // a CoreLabel is the CoreMap with additional methods of token-specific
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {

                System.out.println("\n" + token);

                // this is the text of the token
                String word = token.get(CoreAnnotations.TextAnnotation.class);
                System.out.println("Text Token");
                System.out.println(token + ":" + word);
                // this is the POS tag of the token

                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                System.out.println("Lemmatization");
                System.out.println(token + ":" + lemma);
                // this is the Lemmatized tag of the token

                String pos = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                System.out.println("Parts of Speech(POS)");
                System.out.println(token + ":" + pos);

                // this is the NER label of the token
                String ne = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);
                System.out.println("Name Entity Recognizer");
                System.out.println(token + ":" + ne);

                System.out.println("\n\n");
            }

            // this is the parse tree of the current sentence
            Tree tree = sentence.get(TreeCoreAnnotations.TreeAnnotation.class);
            System.out.println(tree);
            // this is the Stanford dependency graph of the current sentence
            SemanticGraph dependencies = sentence.get(SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation.class);
            System.out.println(dependencies.toString());

            Map<Integer, CorefChain> graph =
                    document.get(CorefCoreAnnotations.CorefChainAnnotation.class);
            System.out.println(graph.values().toString());

        }
    }

    public static String readText(String fileName) throws IOException {
        BufferedReader buffer = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sting= new StringBuilder();
            String line = buffer.readLine();

            while (line != null) {
                sting.append(line);
                sting.append("\n");
                line = buffer.readLine();
            }
            return sting.toString();
        } finally {
            buffer.close();
        }
    }


}
