/**
 * Project/Class Description:
 * This project takes the input file and translate it into the Morse Code version,
 * if the file has all the letters.(ENCODE)
 * This project also takes the input file and translate it into the letters/words version,
 * if the file has all Morse Code.(DECODE)
 * Known bugs: none
 */

// imports

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;

// class MorseCode
public class MorseCode {
    // create a Tree map , the keys are the letters and the values
    // are the corresponding Morse Code
    TreeMap<Character, String> toCode = new TreeMap<>();
    TreeNode<Character> root = new TreeNode<>();

    // constructor build TreeNodes with letters in them,
    // depending on the order of their Morse Code that are sorted.
    MorseCode() throws Exception {
        File morse = new File("MorseCode.txt");
        Scanner s = new Scanner(morse);
        root.letters = " ";

        ArrayList<String> arrayList = new ArrayList<>();

        while (s.hasNextLine()) {
            arrayList.add(s.nextLine());
        }

        for (int i = 0; i < arrayList.size(); i++) {
            String letter = arrayList.get(i).substring(0, 1);
            String code = arrayList.get(i).substring(2);
            String str = code + " " + letter;
            arrayList.set(i, str);
        }
        Collections.sort(arrayList);

        for (int i = 0; i < arrayList.size(); i++) {
            String sr = arrayList.get(i);
            TreeNode parNode = root;
            TreeNode curNode = new TreeNode();
            curNode.letters = sr.substring(sr.length() - 1);
            while (sr.length() > 3) {
                if (sr.charAt(0) == '-') {
                    parNode = parNode.right;
                    sr = sr.replaceFirst(sr.substring(0, 1), "");
                } else {
                    parNode = parNode.left;
                    sr = sr.replaceFirst(sr.substring(0, 1), "");
                }
            }
            if (sr.charAt(0) == '-') {
                parNode.right = curNode;
            } else {
                parNode.left = curNode;
            }
        }
    }

    /**
     * The main calls the methods and pass in the files need to be encoded or decoded
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        MorseCode mor = new MorseCode();
        mor.encodeFile("mcgee.txt", "encoded");
        mor.decodeFile("mcgee.encoded.txt", "decoded");
    }

    /**
     * This method take the input file which is words and translate the whole file
     * into Morse Code then write the Morse Code into a new file, which is the
     * output file.
     * If the input file contains spaces, insert "*".
     * If the input file contains empty lines or after each paragraph, insert "+".
     * Punctuations are ignored.
     *
     * @param inputFilename  is the file that needs to be encoded which contains all the letter.
     * @param outputFilename is the encoded version of the input file.
     * @throws Exception
     */
    public void encodeFile(String inputFilename, String outputFilename) throws Exception {
        PrintWriter output = new PrintWriter(new FileOutputStream(outputFilename));
        File code = new File("MorseCode.txt");
        Scanner scan = new Scanner(code);
        while (scan.hasNext()) {
            Character chars = scan.next().charAt(0);
            String morse = scan.next();
            toCode.put(chars, morse);
        }
        File input = new File(inputFilename);
        Scanner scann = new Scanner(input);
        while (scann.hasNextLine()) {
            String sub = scann.nextLine();
            char[] ary;
            if (sub.length() >= 6 && sub.substring(0, 6).equals("      ")) {
                ary = sub.substring(6).toCharArray();
            } else {
                ary = sub.toCharArray();
            }
            for (char ch : ary) {
                if (toCode.containsKey(ch)) {
                    output.println(toCode.get(ch));
                } else if (ch == ' ') {
                    output.println("*");
                }
            }
            output.println("+");
        }
        output.close();
    }

    /**
     * This method take the input file which is Morse code,
     * then translate them to the corresponding letter according to the built TreeNode.
     * Then write the letter into a new file, which is the
     * output file.
     * Punctuations are ignored.
     *
     * @param inputFilename  is the file that needs to be decoded,which contains all the Morse Code.
     * @param outputFileName is the decoded version of the input file.
     * @throws Exception
     */
    public void decodeFile(String inputFilename, String outputFileName) throws Exception {
        PrintWriter out = new PrintWriter(new FileOutputStream(outputFileName));
        File input = new File(inputFilename);
        Scanner scan = new Scanner(input);
        while (scan.hasNextLine()) {
            String str = scan.nextLine();
            TreeNode node = root;
            while (!str.isEmpty()) {
                if (str.equals("*")) {
                    str = str.replace(str, "");
                    if (str.isEmpty()) {
                        out.print(" ");
                    }
                } else if (str.equals("+")) {
                    str = str.replace(str, "");
                    if (str.isEmpty()) {
                        out.print("\n");
                    }
                } else if (str.substring(0, 1).equals("-")) {
                    node = node.right;
                    str = str.replaceFirst(str.substring(0, 1), "");
                    if (str.isEmpty()) {
                        out.print(node.letters);
                    }
                } else {
                    node = node.left;
                    str = str.replaceFirst(str.substring(0, 1), "");
                    if (str.isEmpty()) {
                        out.print(node.letters);
                    }
                }
            }
        }
        out.close();
    }

    // inner class TreeNode has left and right tree node and Sting letters.
    public static class TreeNode<Character> {
        String letters;
        TreeNode left;
        TreeNode right;
    }
}
