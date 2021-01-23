import java.io.IOException;
import java.net.URL;
import java.util.Scanner;

/**
 * This sample program illustrates how to open a scanner to a url and read the contents
 * one sentence at a time
 *
 * @author Stuart Hansen
 * @version November 2017
 */
public class URLReader {

    public static void main(String[] args) {

        try {
            // Create a URL and Scanner
            URL url = new URL("http://www.gutenberg.org/cache/epub/25546/pg25546.txt");
            Scanner s = new Scanner(url.openStream());

            // Set the delimiter for the scanner
            s.useDelimiter("[\\.\\?\\!]|\\Z");

            // Read until the end of the URL
            while (s.hasNext()) {
                String sentence = s.next();
                System.out.println(sentence);
            }
        } catch (IOException e) {
            System.err.println("Trouble reading from url: " + e);
        }


    }
}
