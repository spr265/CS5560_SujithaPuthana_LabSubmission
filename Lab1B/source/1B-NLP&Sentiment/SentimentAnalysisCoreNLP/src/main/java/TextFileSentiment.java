/**
 * Created by putha on 6/13/2017.
 */
public class TextFileSentiment {

    private String textFile;
    private String cssClassSentiment;

    public TextFileSentiment() {
    }

    public TextFileSentiment(String textFile, String cssClassSentiment) {
        super();
        this.textFile = textFile;
        this.cssClassSentiment = cssClassSentiment;
    }

    public String getLine() {
        return textFile;
    }

    public String getCssClass() {
        return cssClassSentiment;
    }

    @Override
    public String toString() {

        return "Sentiment of File \n [Text File Content \n" + textFile + ",\n Sentiment Analyzed for text File=" + cssClassSentiment + "]";
    }
}
