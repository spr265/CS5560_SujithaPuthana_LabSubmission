/**
 * Created by putha on 6/20/2017.
 */
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
import java.util.*;

public class Questionsrc {
    public static void main(String args[]) throws IOException {
        // creating a StanfordCoreNLP object and perorming Natural language processing
        // Steps: with POS tagging, lemmatization, NER, parsing, and coreference resolution
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Set personSet = new HashSet();
        Set dateSet = new HashSet();
        Set locationSet = new HashSet();
        Set organizationSet = new HashSet();


// read some text in the text variable
        //String text = "This is a sample text"; // Add your text here!
        String text = readFile("src/main/Data/InputFileSuji/tohoku.earthquake.tsunami.8.txt");

// create an empty Annotation just with the given text
        Annotation document = new Annotation(text);

// run all Annotators on this text
        pipeline.annotate(document);

        // these are all the sentences in this document
// a CoreMap is essentially a Map that uses class objects as keys and has values with custom types
        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String nameAndEntity = token.get(CoreAnnotations.NamedEntityTagAnnotation.class);

                if (nameAndEntity.equals("PERSON")) {
                    personSet.add(token);
                }
                if (nameAndEntity.equals("LOCATION")) {
                    locationSet.add(token);
                }
                if (nameAndEntity.equals("ORGANIZATION")) {
                    organizationSet.add(token);
                }
                if (nameAndEntity.equals("DATE")) {
                    dateSet.add(token);
                }


            }
        }
        System.out.println("\n\n");
        System.out.println("Displaying the people mentioned in the article");
        System.out.print(personSet);
        System.out.println("\n\n");
        System.out.println("Displaying the locations mentioned in the article");
        System.out.print(locationSet);
        System.out.println("\n\n");
        System.out.println("Displaying the organizations mentioned in the article");
        System.out.print(organizationSet);
        System.out.println("\n\n");
        System.out.println("Please ask your question based on the above data");
        Scanner scanner = new Scanner(System.in);
        String question = scanner.nextLine();
        if(question.equalsIgnoreCase("What are top 10 high frequency words?")){
            System.out.println(readFile("Data/InputFileSuji/TFIDF_HighFrequency.txt"));
        }
        if(question.equalsIgnoreCase("What is the move that cam after Japan Earthquake")){
            //System.out.println(readFileForNgram("Data/InputFileSuji/N-gramOutput"));
        System.out.println("Monday");
        }

    }

    public static String readFile(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }

    public static String readFileForNgram(String fileName) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null && line.contains("move")) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            return sb.toString();
        } finally {
            br.close();
        }
    }
}


