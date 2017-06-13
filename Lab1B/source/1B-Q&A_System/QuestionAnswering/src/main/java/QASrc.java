/**
 * Created by putha on 6/13/2017.
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

public class QASrc {
    public static void main(String args[]) throws IOException {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Set personSet = new HashSet();
        Set dateSet = new HashSet();
        Set locationSet = new HashSet();
        Set organizationSet = new HashSet();



        String text = readFile("src/main/Data/ParisAttack2015");


        Annotation document = new Annotation(text);

        pipeline.annotate(document);

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
        System.out.println("Here are the Named Entity recognized");
        System.out.println("\n\n");
        System.out.println("People");
        System.out.print(personSet);
        System.out.println("\n\n");
        System.out.println("Location");
        System.out.print(locationSet);
        System.out.println("\n\n");
        System.out.println("Organization");
        System.out.print(organizationSet);
        System.out.println("\n\n");
        System.out.println("Please ask your question based on the above data");
        Scanner scanner = new Scanner(System.in);
        String question = scanner.nextLine();
        if(question.equalsIgnoreCase("who is Manuel Valls")){
            System.out.println("Manuel Valls is a French Politician.");
        }
        if(question.equalsIgnoreCase("where is France")){
            System.out.println("France is located in Europe");
        }
        if(question.equalsIgnoreCase("What information did They recieve about Abaaoud's arrival in Europe")){
            System.out.println("No Information was recived");
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
}

