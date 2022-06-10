/**
 * Project/Class Description:
 *
 * This program builds a data structure called Trie with the Lexicon
 * that contains all correctly spelled words and
 * asks the user to input the name of the file that needs to be checked,
 * then check if each word from that file is contained in the Trie structure.
 * If a word is not found in the Trie, that means that word is spelled wrong,
 * then print out the line number of the miss spelled word appeared in the file with
 * that word, then give the user a list of suggestions which are the correct words in the Lexicon.
 *
 * Known bugs: none
 */

// imports
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Class SpellChecker
public class SpellChecker {
    /**
     * The main asks the user to input the name of the file that needs to be checked,
     * and change each word in the file to lowercase and delete all the non-letter
     * characters, then call containsWord method to check if each word is in the Trie.
     * If a word is not found in the Trie, that means that word is spelled wrong,
     * then print out the line number of the miss spelled word appeared with that word,
     * then give user a list of suggestions which are the correct words in the Lexicon.
     * @param args
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {
        Lexicon lex = new Lexicon();
        // print the header
        System.out.println("Enter name of the file that needs to be checked:");
        // create a scanner to ask the user to input the file name
        Scanner scanner = new Scanner(System.in);
        // create the file
        File file = new File(scanner.next());
        // create another scanner to scan the file user entered
        Scanner scan = new Scanner(file);
        String line;
        // count keeps track of the line number
        int count = 1;
        // a while loop go through all the words in the file
        // and check which word(s) is miss spelled
        while (scan.hasNextLine()) {
            line = scan.nextLine();
            String[] checkWords = line.split(" ");
            for (String words : checkWords) {
                words = words.toLowerCase();
                words = words.replaceAll("[^a-z]", "");
                if (!lex.containsWord(words)&&!words.equals("")) {
                    System.out.println("Word spelled wrong");
                    System.out.println("Line " + count + ": \"" + words + "\" is not spelled correctly");
                    System.out.println("Suggestions:");
                    System.out.println(lex.suggestions(words));
                    System.out.println();
                }
            }
            count++;
        }
        // print out the program finished
        System.out.println("Spell checker program finished.");
    }

    // a inner class Lexicon, build a Trie data structure with all the correct spelled
    // words in the Lexicon.
    private static class Lexicon {
        // root from Node class
        private Node root;

        // Constructor of class Lexicon
        // throws file not found exception
        private Lexicon() throws FileNotFoundException {
            root = new Node();
            // create a file which is the Lexicon
            File file = new File("enable1augmented.txt");
            // create a scanner scan the file
            Scanner scan = null;
            scan = new Scanner(file);
            // A while loop go through the Lexicon and make all the words in the Lexicon
            // lowercase and delete all the non-letter characters,
            // then build the Trie structure containing them.
            // Each node have an array of 26 children,and a boolean value,
            // named isWord, which is set to true only if this node represents a word.
            String word;
            while (scan.hasNextLine()) {
                word = scan.nextLine();
                word = word.toLowerCase();
                word = word.replaceAll("[^a-z]", "");
                Node obj = root;
                for (int i = 0; i < word.length(); i++) {
                    int ind = word.charAt(i) - 'a';
                    if (obj.letters[ind] == null) {
                        Node sc = new Node();
                        obj.letters[ind] = sc;
                        obj = sc;
                    } else {
                        obj = obj.letters[ind];
                    }
                }
                obj.isWord = true;
            }
        }

        // A public method containsWord checks if the Trie structure contains each word
        // in the file that user entered.
        // Returns true if the word is in the lexicon, false otherwise.
        public boolean containsWord(String word) {
            Node obj = root;

            for (int i = 0; i < word.length(); i++) {
                int ind = word.charAt(i) - 'a';
                // System.out.println(obj.letters[ind]);
                if (obj.letters[ind] == null) {
                    return false;
                } else
                    obj = obj.letters[ind];
            }
            return (obj != null && obj.isWord && obj != root);
        }

        // suggestions method generates the suggestions for the word(s) miss spelled
        // by swapping, inserting letters, deleting letters, replacing letter,
        // and splitting words.
        // Returns a list of sorted suggestions.
        private List<String> suggestions(String word) {
            // a hashset that takes all the suggestions for the miss spelled word
            // no duplicates.
            HashSet<String> sugs = new HashSet<>();
            // a character array of letters in alphabet
            char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

            // Splitting.
            // Split the word into a pair of words.
            // If both words in the pair are found in the lexicon then put
            // the words into the hashset.
            String comb;
            for (int i = 1; i < word.length(); i++) {
                String part1 = word.substring(0, i);
                String part2 = word.substring(i);
                if (containsWord(part1) && containsWord(part2)) {
                    comb = part1 + " " + part2;
                    sugs.add(comb);
                }
            }

            // Replacing.
            // Replacing each character in the word with each
            // letter from 'a' through 'z'.
            // If the generated words are found in the lexicon then put
            // the words into the hashset.
            char[] forReplacing = word.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < forReplacing.length; i++) {
                builder.append(forReplacing[i]);
            }
            for (int i = 0; i < builder.length(); i++) {
                for (int j = 0; j < alphabet.length; j++) {
                    String str = builder.replace(i, i + 1, Character.toString(alphabet[j])).toString();
                    if (containsWord(str)) {
                        sugs.add(str);
                    }
                    builder = new StringBuilder();
                    for (int z = 0; z < forReplacing.length; z++) {
                        builder.append(forReplacing[z]);
                    }
                }
            }

            // Removing.
            // Deleting each character from the word.
            // If the generated words are found in the lexicon then put
            // the words into the hashset.
            char[] forRemoving = word.toCharArray();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < forRemoving.length; i++) {
                stringBuilder.append(forRemoving[i]);
            }
            for (int j = 0; j < stringBuilder.length(); j++) {
                String str = stringBuilder.deleteCharAt(j).toString();
                if (containsWord(str)) {
                    sugs.add(str);
                }
                stringBuilder = new StringBuilder();
                for (int i = 0; i < forRemoving.length; i++) {
                    stringBuilder.append(forRemoving[i]);
                }
            }

            // Inserting.
            // In between each adjacent pair of characters in the word
            // (also before the first character and after the last character),
            // insert each letter from 'a' through 'z'.
            // If the generated words are found in the lexicon then put
            // the words into the hashset.
            ArrayList<String> insertedList = new ArrayList<>();
            String insertedStr;
            for (int idx = 0; idx < word.length() + 1; ++idx) {
                for (char ch : alphabet) {
                    insertedStr = word.substring(0, idx) + ch + word.substring(idx);
                    insertedList.add(insertedStr);
                }
            }
            for (String s : insertedList) {
                if (containsWord(s)) {
                    sugs.add(s);
                }
            }

            // Swapping.
            // Swap each adjacent pair of characters in the word.
            // If the generated words are found in the lexicon then put
            // the words into the hashset.
            char[] forSwapping = word.toCharArray();
            for (int i = 1; i < forSwapping.length; i++) {
                char cha = forSwapping[i - 1];
                forSwapping[i - 1] = forSwapping[i];
                forSwapping[i] = cha;
                String string;
                StringBuffer strBuf = new StringBuffer();
                for (int j = 0; j < forSwapping.length; j++) {
                    strBuf.append(forSwapping[i - 1]);
                }
                string = strBuf.toString();
                if (containsWord(string)) {
                    sugs.add(string);
                }
            }

            // use a list to take the hashset with suggestions.
            List<String> list = new ArrayList<>(sugs);
            // sort the list
            Collections.sort(list);
            // return the list
            return list;
        }
    }

    // a public class called Node
    public static class Node {
        // declare a boolean variable isWord
        private boolean isWord;
        // declare an array to take letters
        private Node[] letters;

        // constructor of class Node
        private Node() {
            // the size of the alphabet is 26
            int size = 26;
            // initiate the array with the size of the alphabet
            this.letters = new Node[size];
        }
    }
}
