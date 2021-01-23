/**
 * @author MingFang Li (lim)
 * Course: CSCI 242 - Computer Science II
 * Date: 11/28/2018
 * Assignment: 5
 * Project/Class Description:
 * This program asks the user to input a keyword and 4 different choices,
 * and use cosine similarity to calculate and then show the user which
 * choice has the cloest meaning with the keyword.
 * The program keeps runing until the user enters a blank line.
 * Known bugs: none
 */

// imports
import java.io.IOException;
import java.net.URL;
import java.util.*;

// the class Synonyms
public class Synonyms {
    // create two hash maps in the class
    private HashMap<String, HashMap<String, Integer>> descriptors;
    private HashMap<String, Integer> countHash;

    /**
     * The constructor instantiates the hashmaps and call the method
     * parseCorpus with the URLs array as the parameter.
     *
     * @param corpus is the array of URLs
     */
    public Synonyms(URL[] corpus) {
        countHash = new HashMap<>();
        descriptors = new HashMap<>();
        parseCorpus(corpus);
    }

    /**
     * The main has an array of URLs
     * and ask the users for the input and call the method parseCorpus and
     * calculateCosineSimilarity to pass the files into the hashmap and calculate
     * the similarity of two words.
     * The program keeps running until the user enters a blank line.
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        URL[] corpus = {
                // The Adventures of Sherlock holmes, by A. Conan Doyle
                new URL("https://www.gutenberg.org/files/48320/48320-0.txt"),
                // The Hound of the Baskerfilles, by A. Conan Doyle
                new URL("https://www.gutenberg.org/files/2852/2852-0.txt"),
                // The Mysterious Affair at Styles, by Agatha Christie
                new URL("https://www.gutenberg.org/files/863/863-0.txt"),
                // The Secret Adversary, by Agatha Christie
                new URL("https://www.gutenberg.org/files/1155/1155-0.txt"),
                //The Innocence of Father Brown, G.K. Chesterton
                new URL("https://www.gutenberg.org/files/204/204-0.txt"),
                // The Wisdom of Father Brown, by G.K. Chesterton
                new URL("https://www.gutenberg.org/files/223/223-0.txt"),
                // The Thirty-nine Steps, by John Buchan
                new URL("http://www.gutenberg.org/cache/epub/558/pg558.txt"),
                // The Extraordinary Adventures of Arsene Lupin, Gentleman-Burglar, by Maurice Leblanc
                new URL("https://www.gutenberg.org/files/6133/6133-0.txt"),
                // The Mystery of Edwin Drood, by Charles Dickens
                new URL("https://www.gutenberg.org/files/564/564-0.txt")};


        while (true) {
            System.out.println("Enter a word:");
            Scanner scanWord = new Scanner(System.in);
            String enterWord = scanWord.nextLine();
            if (enterWord.isEmpty()) {
                System.out.println("program ended");
                System.exit(0);
            } else {
                System.out.print("Enter the choices");
                System.out.println("(Separate words by a space):");
                Scanner scanAgain = new Scanner(System.in);
                String enterWord2 = scanAgain.nextLine();
                System.out.println("Program running, don't exit, give it a second.");
                String[] strs = enterWord2.split(" ");
                Synonyms call = new Synonyms(corpus);

                double num = -1;
                String synonym = "";
                for (int i = 0; i < strs.length; i++) {
                    double result = call.calculateCosineSimilarity(enterWord, strs[i]);
                    System.out.println("The similarity of the word “" + strs[i] + "” is" + " " + result);
                    if (result > num) {
                        synonym = strs[i];
                        num = result;
                    }
                }
                System.out.println();
                System.out.println("Result:  " + synonym);
            }
            System.out.println();
        }
    }

    /**
     * This method read the files from URLs and pass the files
     * into the hashmaps.
     *
     * @param corpus is the URLs array
     */
    public void parseCorpus(URL[] corpus) {
        for (int i = 0; i < corpus.length; i++) {
            try {
                // the scanner scan through the URLs array and open them in turn.
                Scanner s = new Scanner(corpus[i].openStream());
                // useDelimiter to read a sentence at a time.
                s.useDelimiter("[\\.\\?\\!]|\\Z");
                while (s.hasNext()) {
                    String sentence = s.next();
                    // make the sentences lowercase.
                    sentence = sentence.toLowerCase();
                    // split the sentences into words.
                    String[] stringArray = sentence.trim().split("\\s+");
                    for (String str : stringArray) {
                        // strip out punctuations
                        str = str.replaceAll("[^A-Za-z ]", "");
                        str = str.replaceAll("\\u2014", " ");

                        // create a new inner hashmap for each outer hashmap's key.
                        countHash = new HashMap<>();
                        if (!descriptors.containsKey(str)) {
                            descriptors.put(str, countHash);
                        }
                        for (int j = 0; j < stringArray.length; j++) {
                            stringArray[j] = stringArray[j].replaceAll("[^A-Za-z ]", "");
                            stringArray[j] = stringArray[j].replaceAll(" ", "");
                            if (!(stringArray[j].equals(str))) {
                                if (descriptors.get(str).containsKey(stringArray[j])) {
                                    int num = descriptors.get(str).get(stringArray[j]);
                                    num++;
                                    descriptors.get(str).put(stringArray[j], num);
                                } else {
                                    descriptors.get(str).put(stringArray[j], 1);
                                }
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.err.println("Trouble reading from url: " + e);
            }
        }
    }

    /**
     * This method calculates the similarity between the key word
     * and the choices that user entered.
     * The higher similarity that choice has, the closer meaning it has,
     * with the keyword.
     *
     * @param word1 is the keyword user enters.
     * @param word2 is the choice that is used to compare with the keyword.
     * @return
     */
    public double calculateCosineSimilarity(String word1, String word2) {
        long nu = 0;
        long de = 0;
        long de2 = 0;

        // if word1 or word2 do not appear anywhere in the corpus then return a similarity value of -1.0.
        if (!(descriptors.containsKey(word1)) || !(descriptors.containsKey(word2))) {
            System.out.println("The words entered don't exist in the file.");
            return -1;
        } else {
            Set<String> set = descriptors.get(word1).keySet();
            Iterator<String> it = set.iterator();

            while (it.hasNext()) {
                String key = it.next();
                if (descriptors.get(word2).containsKey(key)) {
                    nu += ((descriptors.get(word1).get(key)) * (descriptors.get(word2).get(key)));
                }
                de += Math.pow((descriptors.get(word1).get(key)), 2);
            }
            Set<String> sett = descriptors.get(word2).keySet();
            Iterator<String> ite = sett.iterator();
            while (ite.hasNext()) {
                String kie = ite.next();
                de2 += Math.pow((descriptors.get(word2).get(kie)), 2);
            }
        }
        return nu / Math.sqrt(de * de2);
    }
}